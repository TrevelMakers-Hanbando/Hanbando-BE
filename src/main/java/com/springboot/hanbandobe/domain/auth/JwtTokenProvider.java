package com.springboot.hanbandobe.domain.auth;

import com.springboot.hanbandobe.domain.auth.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey key;  // JWT 서명에 사용될 비밀 키
    private final long ACCESS_TOKEN_EXP = 1000L * 60L * 30L;          // accessToken : 30분
    private final long REFRESH_TOKEN_EXP = 1000 * 60 * 60 * 24 * 3L;     // refreshToken : 3일

    private final UserDetailsService userDetailsService;
    private final StringRedisTemplate redisTemplate;


    // Base64로 인코딩된 값을(secret key) -> 디코딩하는 과정
    public JwtTokenProvider(
            @Value("${jwt.secret.key}") String secretKey,
            UserDetailsService userDetailsService,
            StringRedisTemplate redisTemplate) {

        // Secret Key를 Base64로 디코딩 -> 바이트 배열로 변환
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        // BASE64 디코딩을 통해 더 안전하게 처리
        this.key = Keys.hmacShaKeyFor(keyBytes);

        this.userDetailsService = userDetailsService;

        this.redisTemplate = redisTemplate;
        log.info("RedisTemplate Class: {}", redisTemplate.getClass());
    }

    // Token 생성
    public String createToken(String email, Collection<? extends GrantedAuthority> authorities, long tokenExp) {
        Map<String, Object> claims = new HashMap<>();

        PrincipalDetails principalDetails = (PrincipalDetails) userDetailsService.loadUserByUsername(email);

        claims.put("email", principalDetails.getUser().getEmail());
        claims.put("userName", principalDetails.getUser().getName());
        // 권한 정보
        claims.put("role", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));
        log.info("Creating token for user: " + claims);

        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .claims(claims)
                .id(UUID.randomUUID().toString()) // 발급 시간 기반보다는 무작위 UUID를 쓰는 편이 충돌 가능성이 더 낮다고 함
                .issuedAt(Date.from(Instant.now())) // 발급 시간
                .expiration(new Date(System.currentTimeMillis() + tokenExp)) // 토큰 만료 기간 설정
                .signWith(key)
                .compact(); // 토큰 직렬화(헤더·페이로드·서명을 Base64URL로 인코딩하고 .으로 연결한 완성된 JWT 문자열을 반환)
    }

    // RefreshToken 생성
    public String createRefreshToken(String email) {

        String refreshToken = createToken(email, List.of(), REFRESH_TOKEN_EXP);
        String rediskey = "refreshToken:" + email;

        log.info("[createRefreshToken] Generated refreshToken for '{}': {}", email, refreshToken);

        try {
            redisTemplate.opsForValue().set(rediskey, refreshToken, REFRESH_TOKEN_EXP, TimeUnit.MILLISECONDS);
            boolean saved = Boolean.TRUE.equals(redisTemplate.hasKey(rediskey));
            log.info("[createRefreshToken] Redis save success? {} - key: {}", saved, rediskey);
        } catch (Exception e) {
            log.error("[createRefreshToken] Redis save failed for key: {}. Reason: {}", rediskey, e.getMessage(), e);
        }

        return refreshToken;
    }

    // 필터에서 인증 성공 시 SecurityContextHolder에 저장할 Authentication 생성
    public Authentication getAuthentication(String token) {

        // Stateless(무상태성)방식
//
//        // JWT 토큰 복호화
//        Claims claims = parseClaims(token);
//        if (claims.get("role") == null) {
//            throw new JwtException("권한 정보가 없는 토큰입니다.");
//        }
//
//        // 클레임에서 권한 정보 가져오기
//        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("role").toString().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .toList();
//        UserDetails principal = new User(claims.getSubject(), "", authorities);

        // 토큰에서 사용자 정보 가져오기
        String email = getUserName(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(
                userDetails, // principal : 인증된 사용자 정보
                "",          // credentials: 이미 검증된 토큰이므로 빈 문자열
                userDetails.getAuthorities()); // authorities : userDetails가 가진 권한 목록
    }

    // 서버에 전달한 토큰 추출 메소드
    public String resolveToken(String bearerToken) {
        log.info("Authorization header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // JWT 토큰에서 클레임 추출
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser() // JWT Parser 생성
                    .verifyWith(key) // 서명 검증에 사용할 비밀 키
                    .build()
                    .parseSignedClaims(token)
                    .getPayload(); // claim 추출
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 Jwt 토큰입니다.", e);
        } catch (Exception e) {
            throw new JwtException("잘못된 Jwt 토큰입니다.", e);
        }
    }

    // 토큰 만료 여부 확인
    public boolean validateToken(String token) {
        return !parseClaims(token).getExpiration().before(new Date());
    }

    // Refresh 토큰 만료 여부 확인
    public boolean validateRefreshToken(String refreshToken) {

        // accessToken에서 사용자 이메일 얻어오기
        String email = getUserName(refreshToken);

        // 사용자 이름을 바탕으로 Redis에서 RefreshToken 얻어오기
        String redisToken = redisTemplate.opsForValue().get("refreshToken:" + email);

        // Redis에 저장된 RefreshToken과도 일치하는지
        return redisToken != null && redisToken.equals(refreshToken);
    }

    // refresh 토큰 삭제
    public void deleteRefreshToken(String token) {
        String email = getUserName(token);
        redisTemplate.delete("refreshToken:" + email);
    }

    public String getUserName(String token) {
        return parseClaims(token).get("email").toString();
    }

    public boolean hasRoleClaim(String token) {
        return parseClaims(token).get("role") != null;
    }

    public long getAccessTokenExpirationTime() {
        return ACCESS_TOKEN_EXP;
    }

    public void addBlackList(String token) {

        // 토큰 발급 시간
        long tokenCreatedTime = parseClaims(token).getIssuedAt().getTime();
        // 토큰 만료 시점까지 남은 시간 계산
        long blackListExp = (tokenCreatedTime + ACCESS_TOKEN_EXP) - System.currentTimeMillis();

        // redis에 저장
        redisTemplate.opsForValue().set(
                "BlackList : " + parseClaims(token).getId(),
                "true",
                blackListExp,
                TimeUnit.MILLISECONDS);
    }

    public boolean isBlackListed(String token) {
        String key = "BlackList : " + parseClaims(token).getId();
        return redisTemplate.hasKey(key);
    }


}

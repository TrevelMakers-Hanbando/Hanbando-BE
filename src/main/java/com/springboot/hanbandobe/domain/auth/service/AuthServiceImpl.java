package com.springboot.hanbandobe.domain.auth.service;

import com.springboot.hanbandobe.domain.auth.JwtTokenProvider;
import com.springboot.hanbandobe.domain.auth.dto.JwtToken;
import com.springboot.hanbandobe.domain.handler.exception.UserException;
import com.springboot.hanbandobe.domain.handler.message.ExceptionMessage;
import com.springboot.hanbandobe.domain.auth.dto.UserLoginDto;
import com.springboot.hanbandobe.domain.user.repository.UserRepository;
import com.springboot.hanbandobe.entity.Role;
import com.springboot.hanbandobe.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public JwtToken signIn(UserLoginDto userLoginDto) {

        // 전달된 이메일, 비밀번호로 인증 요청 토큰 객체 생성
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());

        // authenticationManagerBuilder 이용해 검증 진행
        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(auth);

        // 검증 성공 시 AccessToken, RefreshToken 발급
        String accessToken = jwtTokenProvider.createToken(userLoginDto.getEmail(), authentication.getAuthorities(), jwtTokenProvider.getAccessTokenExpirationTime());
        String refreshToken = jwtTokenProvider.createRefreshToken(userLoginDto.getEmail());

        return new JwtToken(accessToken, refreshToken);
    }

    @Override
    public void logout(String token) {

        // accessToken 추출
        String accessToken = jwtTokenProvider.resolveToken(token);

        // accessToken 블랙리스트 등록
        jwtTokenProvider.addBlackList(accessToken);

        // RefreshToken 삭제
        jwtTokenProvider.deleteRefreshToken(accessToken);

    }

    // 나중에 Refresh 토큰 회전식으로 구현할 예정(현재 고정 토큰 방식)
    @Override
    public JwtToken refresh(String token) {

        // refreshToken 추출
        String refreshToken = jwtTokenProvider.resolveToken(token);

        if(refreshToken == null || !jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new UserException(ExceptionMessage.INVALID_REFRESH_TOKEN);
        }

        // 토큰에서 사용자 추출
        User user = userRepository.findByEmail(jwtTokenProvider.getUserName(refreshToken))
                .orElseThrow(() -> new UserException(ExceptionMessage.USER_NOT_FOUND));

        Role role = user.getRole();

        Collection<GrantedAuthority> authorities = role.getName() != null ?
                List.of(new SimpleGrantedAuthority(role.getName())) : List.of();

        // RefreshToken 이전에 쓰던거 유지
        return new JwtToken(
                jwtTokenProvider.createToken(user.getEmail(), authorities, jwtTokenProvider.getAccessTokenExpirationTime()),
                refreshToken
        );
    }
}

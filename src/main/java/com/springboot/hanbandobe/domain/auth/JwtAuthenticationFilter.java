package com.springboot.hanbandobe.domain.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final List<String> EXCLUDED_PATHS = Arrays.asList("/swagger-ui/**", "/v3/api-docs/**", "/api/user/login", "/api/user/join", "/api/email/send", "/api/email/verify");
    private static final AntPathMatcher pathMacher = new AntPathMatcher();

    // 클라이언트가 새로운 HTTP 요청을 보낼 때마다 실행되는 과정
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(request.getHeader("Authorization"));

        // Swagger UI 경로는 인증 필터를 통과시킴
        if (isExcludedPath(request.getRequestURI())) {
            filterChain.doFilter(request, response);  // JWT 인증을 거치지 않고 바로 필터 통과
            return;
        }

        if (token != null && jwtTokenProvider.validateToken(token)
                && jwtTokenProvider.hasRoleClaim(token)
                && !jwtTokenProvider.isBlackListed(token)) {

            // Authentication 생성
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // Authentication 객체를 SecurityContextHolder에 넣음
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("JWT Token is invalid or blacklisted");

            return;
        }
        filterChain.doFilter(request, response);

    }


    private boolean isExcludedPath(String requestURI) {
        return EXCLUDED_PATHS.stream().anyMatch(pattern -> pathMacher.match(pattern, requestURI));
    }
}

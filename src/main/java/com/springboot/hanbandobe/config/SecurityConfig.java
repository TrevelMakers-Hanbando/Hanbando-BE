package com.springboot.hanbandobe.config;

import com.springboot.hanbandobe.domain.auth.JwtAuthenticationFilter;
import com.springboot.hanbandobe.domain.auth.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) // 세션 쿠키 사용 비활성화
                .cors(corsCustomizer ->
                        corsCustomizer.configurationSource(getCorsConfigurationSource()))

                // 스프링 시큐리티의 기본 http Basic인증, 폼 로그인 기능 비활성화
                // Jwt필터(JwtAuthenticationFilter)가 처리하도록
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                // 세션 사용을 완전히 없애고(STATELESS), 매 요청마다 Authorization 헤더로 인증 정보를 제공해야만 접근이 허용되도록 설정
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        exception ->
                                exception
                                        .authenticationEntryPoint(
                                                (request, response, authException) -> {
                                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                                    response.setContentType("application/json");
                                                    response.setCharacterEncoding("UTF-8");
                                                    response.getWriter().write("{\"error\": \"인증이 필요합니다.\"}");
                                                })
                                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                                            response.setStatus(HttpStatus.FORBIDDEN.value());
                                        }))
                .authorizeHttpRequests(request ->
                        request
                                // Swagger
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                // User
                                .requestMatchers("/api/user/login", "/api/user/join").permitAll()
                                // Email
                                .requestMatchers("/api/email/send", "/api/email/verify").permitAll()
                                // Board-category
                                .requestMatchers(HttpMethod.GET, "/api/board-category/**").permitAll()
                                // Board
                                .requestMatchers(HttpMethod.GET, "/api/board/**").permitAll()
                                // health
                                .requestMatchers("/actuator/**").permitAll()
                                .anyRequest().authenticated()); // 위 경로 외에 다른 요청은 인증필요

        return http.build();
    }

    // CORS 정책 설정 메소드
    private static CorsConfigurationSource getCorsConfigurationSource() {

        return (request) -> {

            // CorsConfiguration 객체 생성
            CorsConfiguration configuration = new CorsConfiguration();

            // CORS 요청에서 허용할 출처를 설정
            configuration.setAllowedOrigins(Arrays.asList("https://www.travel-hanbando.com", "https://travel-hanbando.com","http://localhost:8080", "http://localhost:3000"));

            // CORS 요청에서 허용할 HTTP 메소드 지정
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS"));

            // 클라이언트가 요청 시 사용할 수 있는 헤더 지정
            configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-Type"));

            // 클라이언트가 응답에서 접근할 수 있는 헤더 지정
            configuration.setExposedHeaders(List.of("Authorization"));

            // 자격 증명 허용 여부 설정
            configuration.setAllowCredentials(true);

            // CORS Preflight 요청(OPTIONS 메소드)을 브라우저가 캐싱하는 시간(초 단위)을 설정
            configuration.setMaxAge(3600L);

            return configuration;
        };
    }

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder byCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

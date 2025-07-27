package com.springboot.hanbandobe.domain.auth.controller;

import com.springboot.hanbandobe.domain.auth.dto.JwtToken;
import com.springboot.hanbandobe.domain.auth.dto.UserLoginDto;
import com.springboot.hanbandobe.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "로그인/로그아웃 관련 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일, 비밀번호로 로그인한다.")
    public ResponseEntity<JwtToken> signIn(
            @Valid @RequestBody UserLoginDto userLoginDto) {

        JwtToken jwtToken = authService.signIn(userLoginDto);

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그 아웃", description = "AccessToken 정보를 바탕으로 로그아웃 한다.")
    public ResponseEntity<Void> logOut(
            @RequestHeader("Authorization") String token
    ){
        authService.logout(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급", description = "AccessToken을 Refresh Token 정보를 바탕으로 재발급한다.")
    public ResponseEntity<JwtToken> refresh(
            @RequestHeader("Authorization") String token
    ){
        JwtToken refreshToken = authService.refresh(token);
        return ResponseEntity.ok(refreshToken);
    }

}

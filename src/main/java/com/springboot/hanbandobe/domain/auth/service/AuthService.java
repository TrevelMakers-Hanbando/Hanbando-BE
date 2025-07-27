package com.springboot.hanbandobe.domain.auth.service;

import com.springboot.hanbandobe.domain.auth.dto.JwtToken;
import com.springboot.hanbandobe.domain.auth.dto.UserLoginDto;

public interface AuthService {

    // 로그인
    JwtToken signIn(UserLoginDto userLoginDto);

    // 로그아웃
    void logout(String token);

    // 토큰 재발급
    JwtToken refresh(String token);

}

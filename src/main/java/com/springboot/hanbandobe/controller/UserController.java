package com.springboot.hanbandobe.controller;

import com.springboot.hanbandobe.domain.user.dto.UserRegisterDto;
import com.springboot.hanbandobe.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "회원 가입", description = "회원가입을 통해 유저를 등록한다.")
    public ResponseEntity<String> join(
            @RequestBody UserRegisterDto userRegisterDto
    ) {
        userService.join(userRegisterDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}

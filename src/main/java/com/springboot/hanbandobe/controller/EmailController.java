package com.springboot.hanbandobe.controller;

import com.springboot.hanbandobe.domain.user.dto.EmailDto;
import com.springboot.hanbandobe.domain.user.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
@Tag(name = "Email", description = "이메일 인증 관련 API")
public class EmailController {

    private final EmailService emailService;

    // 인증코드 메일 발송
    @PostMapping("/send")
    @Operation(summary = "이메일 인증코드 발송", description = "입력한 이메일로 인증코드를 발송한다.")
    public ResponseEntity<String> mailSend(@RequestBody EmailDto emailDto) throws MessagingException {
        emailService.sendMessageToEmail(emailDto.getEmail());
        return ResponseEntity.ok("이메일 전송 완료!");
    }

    // 인증코드 인증
    @PostMapping("/verify")
    @Operation(summary = "이메일 인증코드 인증", description = "이메일로 발송된 인증코드를 인증한다.")
    public Boolean verify(@RequestBody EmailDto emailDto) {
        return emailService.verifyEmailCode(emailDto.getEmail(), emailDto.getEmailCode());
    }
}
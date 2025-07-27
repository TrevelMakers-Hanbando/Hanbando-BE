package com.springboot.hanbandobe.domain.handler.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    // 인증 관련
    INVALID_REFRESH_TOKEN("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.",HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}

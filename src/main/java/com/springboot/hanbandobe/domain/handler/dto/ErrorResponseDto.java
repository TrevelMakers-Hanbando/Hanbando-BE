package com.springboot.hanbandobe.domain.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ErrorResponseDto {

    private int code;
    private String status;
    private String message;
}
package com.springboot.hanbandobe.domain.preference.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreferResponseDto {
    private Long userNo;
    private String where;
    private String purpose;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;
}
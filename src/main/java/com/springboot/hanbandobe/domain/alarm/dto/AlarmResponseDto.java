package com.springboot.hanbandobe.domain.alarm.dto;

import com.springboot.hanbandobe.entity.Alarm;
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
public class AlarmResponseDto {
    private Long alarmNo;
    private String title;
    private String content;
    private boolean isRead;
    private LocalDateTime createdAt;

    public static AlarmResponseDto fromEntity(Alarm alarm) {
        return AlarmResponseDto.builder()
                .alarmNo(alarm.getAlarmNo())
                .title(alarm.getTitle())
                .content(alarm.getContent())
                .isRead(alarm.isRead())
                .createdAt(alarm.getCreatedAt())
                .build();
    }
}

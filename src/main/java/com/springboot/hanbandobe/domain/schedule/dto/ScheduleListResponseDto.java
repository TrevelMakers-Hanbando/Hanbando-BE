package com.springboot.hanbandobe.domain.schedule.dto;

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
public class ScheduleListResponseDto {
    private Long ScheduleNo;
    private Long UserNo;
    private String title;
    private LocalDateTime createdAt;
}

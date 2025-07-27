package com.springboot.hanbandobe.domain.schedule_detail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ScheduleDetailPutTimeDto {
    private int startYear;
    private int startMonth;
    private int startDay;
    private int startHour;
    private int startMinute;
    private int startSecond;

    private int endYear;
    private int endMonth;
    private int endDay;
    private int endHour;
    private int endMinute;
    private int endSecond;

    @Schema(hidden = true)
    public LocalDateTime getStartedAt() {
        return LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute, startSecond);
    }

    @Schema(hidden = true)
    public LocalDateTime getEndedAt() {
        return LocalDateTime.of(endYear, endMonth, endDay, endHour, endMinute, endSecond);
    }
}

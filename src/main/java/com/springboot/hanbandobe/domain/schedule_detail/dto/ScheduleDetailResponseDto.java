package com.springboot.hanbandobe.domain.schedule_detail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.hanbandobe.entity.Schedule_detail;
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
public class ScheduleDetailResponseDto {
    private Long scheduleDetailNo;
    private String travelCategoryName;
    private Boolean isSelected;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public static ScheduleDetailResponseDto from(Schedule_detail scheduleDetail) {
        return ScheduleDetailResponseDto.builder()
                .scheduleDetailNo(scheduleDetail.getScheduleDetailNo())
                .travelCategoryName(scheduleDetail.getTravelCategory().getName())
                .isSelected(scheduleDetail.getIsSelected())
                .title(scheduleDetail.getTitle())
                .content(scheduleDetail.getContent())
                .startedAt(scheduleDetail.getStartedAt())
                .endedAt(scheduleDetail.getEndedAt())
                .createdAt(scheduleDetail.getCreatedAt())
                .updatedAt(scheduleDetail.getUpdatedAt())
                .build();
    }

}

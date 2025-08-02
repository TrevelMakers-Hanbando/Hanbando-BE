package com.springboot.hanbandobe.domain.schedule_detail.dto;

import com.springboot.hanbandobe.entity.Schedule;
import com.springboot.hanbandobe.entity.Schedule_detail;
import com.springboot.hanbandobe.entity.Travel_category;
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
public class PostPreferDto {
    private String title;
    private String content;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Long travelCategoryNo;

    public Schedule_detail toEntity(Schedule schedule, Travel_category travelCategory) {
        return Schedule_detail.builder()
                .schedule(schedule)
                .travelCategory(travelCategory)
                .title(this.title)
                .content(this.content)
                .startedAt(this.startedAt)
                .endedAt(this.endedAt)
                .isSelected(false)
                .build();
    }

}

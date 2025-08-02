package com.springboot.hanbandobe.domain.schedule_detail.service;

import com.springboot.hanbandobe.domain.schedule_detail.dto.PostPreferDto;
import com.springboot.hanbandobe.domain.schedule_detail.dto.ScheduleDetailPutTimeDto;
import com.springboot.hanbandobe.domain.schedule_detail.dto.ScheduleDetailResponseDto;
import com.springboot.hanbandobe.domain.schedule_detail.dto.testDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleDetailService {
    List<ScheduleDetailResponseDto> GetScheduleDetails(Long ScheduleNo);

    ScheduleDetailResponseDto PutScheduleDetailSelect(Long ScheduleDetailNo);

    ScheduleDetailResponseDto PutScheduleDetailCancel(Long ScheduleDetailNo);

    ScheduleDetailResponseDto PutScheduleDetail(Long ScheduleDetailNo, @Valid ScheduleDetailPutTimeDto scheduleDetailPutTimeDto);

    ScheduleDetailResponseDto GetScheduleDetail(Long ScheduleDetailNo);

    List<ScheduleDetailResponseDto> GetScheduleDetailDetail(Long ScheduleNo);

    String DeleteScheduleDetail(Long ScheduleDetailNo);

    List<PostPreferDto> PostScheduleDetails(Long userNo, LocalDateTime startDate, LocalDateTime endDate);
}

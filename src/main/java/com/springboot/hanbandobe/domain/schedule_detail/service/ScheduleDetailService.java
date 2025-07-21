package com.springboot.hanbandobe.domain.schedule_detail.service;

import com.springboot.hanbandobe.domain.schedule_detail.dto.ScheduleDetailResponseDto;

import java.util.List;

public interface ScheduleDetailService {
    List<ScheduleDetailResponseDto> GetScheduleDetails(Long ScheduleNo);

    ScheduleDetailResponseDto PutScheduleDetailSelect(Long ScheduleDetailNo);
}

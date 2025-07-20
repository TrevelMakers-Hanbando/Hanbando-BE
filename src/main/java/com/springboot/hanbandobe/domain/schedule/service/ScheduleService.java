package com.springboot.hanbandobe.domain.schedule.service;

import com.springboot.hanbandobe.domain.schedule.dto.ScheduleRequestDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleResponseDto;

public interface ScheduleService {
    ScheduleResponseDto PostSchedule(Long userNo, ScheduleRequestDto scheduleRequestDto);
}

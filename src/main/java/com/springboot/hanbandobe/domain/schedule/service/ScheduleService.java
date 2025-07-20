package com.springboot.hanbandobe.domain.schedule.service;

import com.springboot.hanbandobe.domain.schedule.dto.ScheduleDetailResponseDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleListResponseDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleRequestDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto PostSchedule(Long userNo, ScheduleRequestDto scheduleRequestDto);

    List<ScheduleListResponseDto> getSchedule(int page, int size);

    ScheduleDetailResponseDto getScheduleDetail(Long scheduleNo);
}

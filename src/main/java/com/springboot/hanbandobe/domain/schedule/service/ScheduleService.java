package com.springboot.hanbandobe.domain.schedule.service;

import com.springboot.hanbandobe.domain.schedule.dto.ScheduleDetailResponseDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleListResponseDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleRequestDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto PostSchedule(Long userNo, ScheduleRequestDto scheduleRequestDto);

    List<ScheduleListResponseDto> getSchedule(int page, int size);

    ScheduleDetailResponseDto getScheduleDetail(Long scheduleNo);

    ScheduleResponseDto PutScheduleDetail(Long ScheduleNo, @Valid ScheduleRequestDto scheduleRequestDto);

    void DeleteSchedule(Long scheduleNo);

    ScheduleResponseDto PatchScheduleDetail(Long scheduleNo, @Valid ScheduleRequestDto scheduleRequestDto);
}

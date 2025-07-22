package com.springboot.hanbandobe.domain.schedule_detail.service;

import com.springboot.hanbandobe.domain.schedule_detail.dto.ScheduleDetailPutTimeDto;
import com.springboot.hanbandobe.domain.schedule_detail.dto.ScheduleDetailResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ScheduleDetailService {
    List<ScheduleDetailResponseDto> GetScheduleDetails(Long ScheduleNo);

    ScheduleDetailResponseDto PutScheduleDetailSelect(Long ScheduleDetailNo);

    ScheduleDetailResponseDto PutScheduleDetailCancel(Long ScheduleDetailNo);

    ScheduleDetailResponseDto PutScheduleDetail(Long ScheduleDetailNo, @Valid ScheduleDetailPutTimeDto scheduleDetailPutTimeDto);
}

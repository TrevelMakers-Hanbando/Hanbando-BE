package com.springboot.hanbandobe.domain.alarm.service;

import com.springboot.hanbandobe.domain.alarm.dto.AlarmRequestDto;
import com.springboot.hanbandobe.domain.alarm.dto.AlarmResponseDto;
import jakarta.validation.Valid;

import java.util.List;


public interface AlarmService {
    AlarmResponseDto createAlarm(Long userNo, @Valid AlarmRequestDto alarmRequestDto);

    AlarmResponseDto putAlarm(Long userNo, Long AlarmNo);

    List<AlarmResponseDto> getAlarms(Long userNo, int page, int size);

    String deleteAlarm(Long userNo, Long AlarmNo);
}

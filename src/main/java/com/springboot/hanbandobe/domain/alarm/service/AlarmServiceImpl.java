package com.springboot.hanbandobe.domain.alarm.service;

import com.springboot.hanbandobe.domain.alarm.dto.AlarmRequestDto;
import com.springboot.hanbandobe.domain.alarm.dto.AlarmResponseDto;
import com.springboot.hanbandobe.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    private final AlarmRepository alarmRepository;

    @Override
    public AlarmResponseDto createAlarm(Long userNo, AlarmRequestDto alarmRequestDto) {


        return null;
    }

    @Override
    public AlarmResponseDto getAlarm(Long userNo, Long AlarmNo) {
        return null;
    }

    @Override
    public List<AlarmResponseDto> getAlarms(Long userNo, int page, int size) {
        return List.of();
    }

    @Override
    public String deleteAlarm(Long userNo, Long AlarmNo) {
        return "";
    }
}

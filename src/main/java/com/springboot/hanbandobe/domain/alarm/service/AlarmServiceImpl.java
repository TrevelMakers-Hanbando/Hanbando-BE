package com.springboot.hanbandobe.domain.alarm.service;

import com.springboot.hanbandobe.domain.alarm.dto.AlarmRequestDto;
import com.springboot.hanbandobe.domain.alarm.dto.AlarmResponseDto;
import com.springboot.hanbandobe.domain.alarm.repository.AlarmRepository;
import com.springboot.hanbandobe.domain.user.repository.UserRepository;
import com.springboot.hanbandobe.entity.Alarm;
import com.springboot.hanbandobe.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    @Override
    public AlarmResponseDto createAlarm(Long userNo, AlarmRequestDto alarmRequestDto) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("유저 정보가 존재하지 않습니다."));

        Alarm alarm = Alarm.builder()
                .user(user)
                .title(alarmRequestDto.getTitle())
                .content(alarmRequestDto.getContent())
                .build();

        Alarm savedAlarm = alarmRepository.save(alarm);

        return AlarmResponseDto.fromEntity(savedAlarm);
    }

    @Override
    public AlarmResponseDto putAlarm(Long userNo, Long AlarmNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("유저 정보가 존재하지 않습니다."));

        Alarm alarm = alarmRepository.findById(AlarmNo)
                .orElseThrow(() -> new RuntimeException("알람이 존재하지 않습니다."));

        if (!alarm.getUser().getUserNo().equals(user.getUserNo())) throw new RuntimeException("해당 알람에 접근할 수 없습니다.");

        alarm.setRead(true);
        Alarm updatedAlarm = alarmRepository.save(alarm);

        return AlarmResponseDto.fromEntity(updatedAlarm);
    }

    @Override
    public List<AlarmResponseDto> getAlarms(Long userNo, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("유저 정보가 존재하지 않습니다."));

        Page<Alarm> alarms = alarmRepository.findByUser(user, pageRequest);

        return alarms.stream()
                .map(AlarmResponseDto::fromEntity)
                .toList();
    }

    @Override
    public String deleteAlarm(Long userNo, Long AlarmNo) {
        Alarm alarm = alarmRepository.findById(AlarmNo)
                .orElseThrow(() -> new RuntimeException("해당 알람이 존재하지 않습니다."));
        alarmRepository.delete(alarm);

        return "삭제되었습니다.";
    }
}

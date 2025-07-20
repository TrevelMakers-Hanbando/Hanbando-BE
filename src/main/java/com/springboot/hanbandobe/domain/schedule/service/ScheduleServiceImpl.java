package com.springboot.hanbandobe.domain.schedule.service;

import com.springboot.hanbandobe.domain.schedule.dto.ScheduleDetailResponseDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleListResponseDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleRequestDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleResponseDto;
import com.springboot.hanbandobe.domain.schedule.repository.ScheduleRepository;
import com.springboot.hanbandobe.domain.user.repository.UserRepository;
import com.springboot.hanbandobe.entity.Schedule;
import com.springboot.hanbandobe.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResponseDto PostSchedule(Long userNo, ScheduleRequestDto scheduleRequestDto) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));

        Schedule schedule = Schedule.builder()
                .user(user)
                .title(scheduleRequestDto.getTitle())
                .content(scheduleRequestDto.getContent())
                .startedAt(scheduleRequestDto.getStartedAt())
                .endedAt(scheduleRequestDto.getEndedAt())
                .build();

        scheduleRepository.save(schedule);

        return ScheduleResponseDto.builder()
                .scheduleNo(schedule.getScheduleNo())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .startedAt(schedule.getStartedAt())
                .endedAt(schedule.getEndedAt())
                .message("일정 생성 성공")
                .build();
    }

    @Override
    public List<ScheduleListResponseDto> getSchedule(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return scheduleRepository.findAll(pageRequest)
                .map(Schedule -> new ScheduleListResponseDto(
                        Schedule.getScheduleNo(),
                        Schedule.getUser().getUserNo(),
                        Schedule.getTitle(),
                        Schedule.getCreatedAt()
                )).getContent();
    }

    @Override
    public ScheduleDetailResponseDto getScheduleDetail(Long scheduleNo) {
        Schedule schedule = scheduleRepository.findById(scheduleNo)
                .orElseThrow(() -> new RuntimeException("등록되지 않은 스케줄입니다."));

        return ScheduleDetailResponseDto.builder()
                .ScheduleNo(schedule.getScheduleNo())
                .userNo(schedule.getUser().getUserNo())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .startedAt(schedule.getStartedAt())
                .endedAt(schedule.getEndedAt())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt())
                .build();
    }
}

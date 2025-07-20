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

    @Override
    public ScheduleResponseDto PutScheduleDetail(Long ScheduleNo, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(ScheduleNo)
                .orElseThrow(() -> new RuntimeException("해당 스케줄이 존재하지 않습니다."));

        schedule.setTitle(scheduleRequestDto.getTitle());
        schedule.setContent(scheduleRequestDto.getContent());
        schedule.setStartedAt(scheduleRequestDto.getStartedAt());
        schedule.setEndedAt(scheduleRequestDto.getEndedAt());

        scheduleRepository.save(schedule);

        return ScheduleResponseDto.builder()
                .scheduleNo(schedule.getScheduleNo())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .startedAt(schedule.getStartedAt())
                .endedAt(schedule.getEndedAt())
                .message("일정 수정 성공")
                .build();
    }

    @Override
    public void DeleteSchedule(Long scheduleNo) {
        Schedule schedule = scheduleRepository.findById(scheduleNo)
                .orElseThrow(() -> new RuntimeException("해당 스케줄은 존재하지 않습니다."));
        scheduleRepository.delete(schedule);
    }

    @Override
    public ScheduleResponseDto PatchScheduleDetail(Long scheduleNo, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleNo)
                .orElseThrow(() -> new RuntimeException("해당 스케줄은 존재하지 않습니다."));

        if(scheduleRequestDto.getTitle() != null) schedule.setTitle(scheduleRequestDto.getTitle());
        if(scheduleRequestDto.getContent() != null) schedule.setContent(scheduleRequestDto.getContent());
        if(scheduleRequestDto.getStartedAt() != null) schedule.setStartedAt(scheduleRequestDto.getStartedAt());
        if(scheduleRequestDto.getEndedAt() != null) schedule.setEndedAt(scheduleRequestDto.getEndedAt());

        Schedule patchSchedule = scheduleRepository.save(schedule);

        return ScheduleResponseDto.builder()
                .scheduleNo(patchSchedule.getScheduleNo())
                .title(patchSchedule.getTitle())
                .content(patchSchedule.getContent())
                .startedAt(patchSchedule.getStartedAt())
                .endedAt(patchSchedule.getEndedAt())
                .message("일정 일부 수정 완료")
                .build();
    }
}

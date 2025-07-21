package com.springboot.hanbandobe.domain.schedule_detail.service;

import com.springboot.hanbandobe.domain.schedule.repository.ScheduleRepository;
import com.springboot.hanbandobe.domain.schedule_detail.dto.ScheduleDetailResponseDto;
import com.springboot.hanbandobe.domain.schedule_detail.repository.ScheduleDetailRepository;
import com.springboot.hanbandobe.entity.Schedule_detail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleDetailServiceImpl implements ScheduleDetailService {
    private final ScheduleDetailRepository scheduleDetailRepository;
    private final ScheduleRepository scheduleRepository;
    @Override
    public List<ScheduleDetailResponseDto> GetScheduleDetails(Long ScheduleNo) {

        if (!scheduleRepository.existsById(ScheduleNo)) {
            throw new RuntimeException("해당 스케줄이 존재하지 않습니다.");
        }

        List<Schedule_detail> scheduleDetails = scheduleDetailRepository.findAllWithCategoryByScheduleNo(ScheduleNo);

        return scheduleDetails.stream()
                .map(sd -> ScheduleDetailResponseDto.builder()
                        .scheduleDetailNo(sd.getScheduleDetailNo())
                        .travelCategoryName(sd.getTravelCategory().getName())  // travel_category.name
                        .isSelected(sd.getIsSelected())
                        .title(sd.getTitle())
                        .content(sd.getContent())
                        .startedAt(sd.getStartedAt())
                        .endedAt(sd.getEndedAt())
                        .createdAt(sd.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public ScheduleDetailResponseDto PutScheduleDetailSelect(Long ScheduleDetailNo) {
        Schedule_detail scheduleDetail = scheduleDetailRepository.findById(ScheduleDetailNo)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 일정입니다."));

        if (Boolean.TRUE.equals(scheduleDetail.getIsSelected())) throw new RuntimeException("이미 선택된 일정입니다.");


        scheduleDetail.setIsSelected(Boolean.FALSE);
        scheduleDetailRepository.save(scheduleDetail);

        return ScheduleDetailResponseDto.builder()
                .scheduleDetailNo(scheduleDetail.getScheduleDetailNo())
                .travelCategoryName(scheduleDetail.getTravelCategory().getName())
                .isSelected(scheduleDetail.getIsSelected())
                .title(scheduleDetail.getContent())
                .content(scheduleDetail.getContent())
                .startedAt(scheduleDetail.getStartedAt())
                .endedAt(scheduleDetail.getEndedAt())
                .createdAt(scheduleDetail.getStartedAt())
                .build();
    }

    @Override
    public ScheduleDetailResponseDto PutScheduleDetailCancel(Long ScheduleDetailNo) {
        Schedule_detail scheduleDetail = scheduleDetailRepository.findById(ScheduleDetailNo)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 일정입니다."));

        if (Boolean.FALSE.equals(scheduleDetail.getIsSelected())) throw new RuntimeException("선택된 적이 없는 일정입니다.");

        scheduleDetail.setIsSelected(Boolean.TRUE);
        scheduleDetailRepository.save(scheduleDetail);

        return ScheduleDetailResponseDto.builder()
                .scheduleDetailNo(scheduleDetail.getScheduleDetailNo())
                .travelCategoryName(scheduleDetail.getTravelCategory().getName())
                .isSelected(scheduleDetail.getIsSelected())
                .title(scheduleDetail.getContent())
                .content(scheduleDetail.getContent())
                .startedAt(scheduleDetail.getStartedAt())
                .endedAt(scheduleDetail.getEndedAt())
                .createdAt(scheduleDetail.getStartedAt())
                .build();
    }
}

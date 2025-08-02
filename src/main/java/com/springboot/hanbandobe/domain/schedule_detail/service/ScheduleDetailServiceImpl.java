package com.springboot.hanbandobe.domain.schedule_detail.service;

import com.springboot.hanbandobe.domain.preference.repository.PreferRepository;
import com.springboot.hanbandobe.domain.schedule.repository.ScheduleRepository;
import com.springboot.hanbandobe.domain.schedule_detail.dto.PostPreferDto;
import com.springboot.hanbandobe.domain.schedule_detail.dto.ScheduleDetailPutTimeDto;
import com.springboot.hanbandobe.domain.schedule_detail.dto.ScheduleDetailResponseDto;
import com.springboot.hanbandobe.domain.schedule_detail.dto.testDto;
import com.springboot.hanbandobe.domain.schedule_detail.repository.ScheduleDetailRepository;
import com.springboot.hanbandobe.domain.travel_category.repository.travelCategoryRepository;
import com.springboot.hanbandobe.entity.Prefer;
import com.springboot.hanbandobe.entity.Schedule;
import com.springboot.hanbandobe.entity.Schedule_detail;
import com.springboot.hanbandobe.entity.Travel_category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleDetailServiceImpl implements ScheduleDetailService {
    private final ScheduleDetailRepository scheduleDetailRepository;
    private final ScheduleRepository scheduleRepository;
    private final PreferRepository preferRepository;
    private final travelCategoryRepository travelCategoryRepository;

    @Override
    public List<ScheduleDetailResponseDto> GetScheduleDetails(Long ScheduleNo) {

        if (!scheduleRepository.existsById(ScheduleNo)) {
            throw new RuntimeException("해당 스케줄이 존재하지 않습니다.");
        }

        List<Schedule_detail> scheduleDetails = scheduleDetailRepository.findAllWithCategoryByScheduleNo(ScheduleNo);

        return scheduleDetails.stream()
                .map(sd -> ScheduleDetailResponseDto.builder()
                        .scheduleDetailNo(sd.getScheduleDetailNo())
                        .travelCategoryName(sd.getTravelCategory().getName())
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


        scheduleDetail.setIsSelected(Boolean.TRUE);
        scheduleDetail.setUpdatedAt(LocalDateTime.now());
        scheduleDetailRepository.save(scheduleDetail);

        return ScheduleDetailResponseDto.builder()
                .scheduleDetailNo(scheduleDetail.getScheduleDetailNo())
                .travelCategoryName(scheduleDetail.getTravelCategory().getName())
                .isSelected(scheduleDetail.getIsSelected())
                .title(scheduleDetail.getTitle())
                .content(scheduleDetail.getContent())
                .startedAt(scheduleDetail.getStartedAt())
                .endedAt(scheduleDetail.getEndedAt())
                .createdAt(scheduleDetail.getCreatedAt())
                .updatedAt(scheduleDetail.getUpdatedAt())
                .build();
    }

    @Override
    public ScheduleDetailResponseDto PutScheduleDetailCancel(Long ScheduleDetailNo) {
        Schedule_detail scheduleDetail = scheduleDetailRepository.findById(ScheduleDetailNo)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 일정입니다."));

        if (Boolean.FALSE.equals(scheduleDetail.getIsSelected())) throw new RuntimeException("선택된 적이 없는 일정입니다.");

        scheduleDetail.setIsSelected(Boolean.FALSE);
        scheduleDetail.setUpdatedAt(LocalDateTime.now());
        scheduleDetailRepository.save(scheduleDetail);

        return ScheduleDetailResponseDto.builder()
                .scheduleDetailNo(scheduleDetail.getScheduleDetailNo())
                .travelCategoryName(scheduleDetail.getTravelCategory().getName())
                .isSelected(scheduleDetail.getIsSelected())
                .title(scheduleDetail.getTitle())
                .content(scheduleDetail.getContent())
                .startedAt(scheduleDetail.getStartedAt())
                .endedAt(scheduleDetail.getEndedAt())
                .createdAt(scheduleDetail.getCreatedAt())
                .updatedAt(scheduleDetail.getUpdatedAt())
                .build();
    }

    @Override
    public ScheduleDetailResponseDto PutScheduleDetail(Long scheduleDetailNo, ScheduleDetailPutTimeDto dto) {
        Schedule_detail scheduleDetail = scheduleDetailRepository.findById(scheduleDetailNo)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 일정입니다."));

        LocalDateTime newStart = dto.getStartedAt();
        LocalDateTime newEnd = dto.getEndedAt();

        Schedule schedule = scheduleDetail.getSchedule();
        if (newStart.isBefore(schedule.getStartedAt()) || newEnd.isAfter(schedule.getEndedAt())) {
            throw new RuntimeException("일정 시간은 전체 스케줄 범위를 벗어날 수 없습니다.");
        }

        List<Schedule_detail> selectedDetails = scheduleDetailRepository.findBySchedule_ScheduleNoAndIsSelectedTrue(schedule.getScheduleNo());

        for (Schedule_detail other : selectedDetails) {
            if (!other.getScheduleDetailNo().equals(scheduleDetailNo)) {
                LocalDateTime otherStart = other.getStartedAt();
                LocalDateTime otherEnd = other.getEndedAt();

                boolean overlaps = !(newEnd.isBefore(otherStart) || newStart.isAfter(otherEnd));
                if (overlaps) {
                    throw new RuntimeException("겹치는 선택된 일정이 이미 존재합니다.");
                }
            }
        }

        scheduleDetail.setStartedAt(newStart);
        scheduleDetail.setEndedAt(newEnd);
        scheduleDetail.setUpdatedAt(LocalDateTime.now());

        scheduleDetailRepository.save(scheduleDetail);

        return ScheduleDetailResponseDto.builder()
                .scheduleDetailNo(scheduleDetail.getScheduleDetailNo())
                .travelCategoryName(scheduleDetail.getTravelCategory().getName())
                .isSelected(scheduleDetail.getIsSelected())
                .title(scheduleDetail.getTitle())
                .content(scheduleDetail.getContent())
                .startedAt(scheduleDetail.getStartedAt())
                .endedAt(scheduleDetail.getEndedAt())
                .createdAt(scheduleDetail.getCreatedAt())
                .updatedAt(scheduleDetail.getUpdatedAt())
                .build();
    }

    @Override
    public ScheduleDetailResponseDto GetScheduleDetail(Long ScheduleDetailNo) {
        Schedule_detail scheduleDetail = scheduleDetailRepository.findById(ScheduleDetailNo)
                .orElseThrow(() -> new RuntimeException("해당 스케줄은 존재하지 않습니다."));

        return ScheduleDetailResponseDto.from(scheduleDetail);
    }

    @Override
    public List<ScheduleDetailResponseDto> GetScheduleDetailDetail(Long ScheduleNo) {
        if (!scheduleRepository.existsById(ScheduleNo)) {
            throw new RuntimeException("해당 스케줄이 존재하지 않습니다.");
        }

        List<Schedule_detail> selectedDetails = scheduleDetailRepository
                .findSelectedWithCategoryByScheduleNo(ScheduleNo);

        return selectedDetails.stream()
                .map(sd -> ScheduleDetailResponseDto.builder()
                        .scheduleDetailNo(sd.getScheduleDetailNo())
                        .travelCategoryName(sd.getTravelCategory().getName())
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
    public String DeleteScheduleDetail(Long ScheduleDetailNo) {
        Schedule_detail sd = scheduleDetailRepository.findById(ScheduleDetailNo)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 스케줄입니다."));

        scheduleDetailRepository.delete(sd);

        return "해당 스케줄을 삭제했습니다.";
    }

    private static final String PYTHON_POST = "http://127.0.0.1:5002";

    @Override
    @Transactional
    public List<PostPreferDto> PostScheduleDetails(Long userNo, LocalDateTime startDate, LocalDateTime endDate) {
        Prefer.PreferId preferId = new Prefer.PreferId(userNo);
        Prefer prefer = preferRepository.findById(preferId)
                .orElseThrow(() -> new RuntimeException("선호도가 존재하지 않습니다."));

        // 2. request payload 구성
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("userNo", userNo);
        requestPayload.put("where", prefer.getWhere());
        requestPayload.put("content", prefer.getContent());
        requestPayload.put("purpose", prefer.getPurpose());
        requestPayload.put("startDate", startDate.toString());
        requestPayload.put("endDate", endDate.toString());

        // 3. Flask 요청
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestPayload, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<PostPreferDto>> response = restTemplate.exchange(
                PYTHON_POST + "/test-connection",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        List<PostPreferDto> responseDtoList = response.getBody();

        if (responseDtoList == null || responseDtoList.isEmpty()) {
            throw new RuntimeException("일정 생성 응답이 비어 있습니다.");
        }

        // 4. 사용자 스케줄 조회
        Schedule schedule = scheduleRepository.findByUserUserNo(userNo)
                .orElseThrow(() -> new RuntimeException("스케줄이 존재하지 않습니다."));

        // 5. DTO → Entity 변환 및 저장
        List<Schedule_detail> entities = responseDtoList.stream()
                .map(dto -> {
                    Travel_category travelCategory = travelCategoryRepository.findById(dto.getTravelCategoryNo())
                            .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));
                    return dto.toEntity(schedule, travelCategory);
                })
                .collect(Collectors.toList());

        scheduleDetailRepository.saveAll(entities);

        return responseDtoList;
    }
}

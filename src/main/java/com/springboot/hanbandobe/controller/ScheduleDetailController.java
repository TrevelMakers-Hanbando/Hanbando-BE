package com.springboot.hanbandobe.controller;

import com.springboot.hanbandobe.domain.schedule_detail.dto.ScheduleDetailPutTimeDto;
import com.springboot.hanbandobe.domain.schedule_detail.dto.ScheduleDetailResponseDto;
import com.springboot.hanbandobe.domain.schedule_detail.service.ScheduleDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/schedule-details")
@Tag(name = "ScheduleDetail", description = "스케줄 관련 API")
@RequiredArgsConstructor
public class ScheduleDetailController {
    private final ScheduleDetailService scheduleDetailService;

    @PostMapping
    @Operation(summary = "스케줄 생성", description = "새 스케줄을 추가합니다.")
    public ResponseEntity<?> PostScheduleDetails() {
        // prefer 정보를 flask에 넘겨서 받아와서 scheduleDetail에 저장
        return null;
    }

    @GetMapping
    @Operation(summary = "스케줄 상세보기", description = "스케줄의 상세한 정보를 조회합니다.")
    public ResponseEntity<ScheduleDetailResponseDto> GetScheduleDetail(
            @RequestParam Long ScheduleDetailNo
    ) {
        return ResponseEntity.ok(scheduleDetailService.GetScheduleDetail(ScheduleDetailNo));
    }

    @GetMapping("/list")
    @Operation(summary = "추천 리스트 조회", description = "추천 스케줄 리스트를 조회합니다.")
    public ResponseEntity<List<ScheduleDetailResponseDto>> GetScheduleDetails(
            @RequestParam Long ScheduleNo
    ) {
        return ResponseEntity.ok(scheduleDetailService.GetScheduleDetails(ScheduleNo));
    }

    @PutMapping("/select")
    @Operation(summary = "스케줄 선택", description = "해당 스케줄을 선택합니다.")
    public ScheduleDetailResponseDto PutScheduleDetailSelect(
            @RequestParam Long ScheduleDetailNo
    ) {
        return scheduleDetailService.PutScheduleDetailSelect(ScheduleDetailNo);
    }

    @PutMapping("/cancel")
    @Operation(summary = "스케줄 선택 취소", description = "해당 스케줄 선택을 취소합니다.")
    public ScheduleDetailResponseDto PutScheduleDetailCancel(
            @RequestParam Long ScheduleDetailNo
    ) {
        return scheduleDetailService.PutScheduleDetailCancel(ScheduleDetailNo);
    }

    @PutMapping
    @Operation(summary = "스케줄 시간대 수정", description = "해당 스케줄의 시간대를 변경합니다.")
    public ScheduleDetailResponseDto PutScheduleDetail(
            @RequestParam Long ScheduleDetailNo,
            @RequestBody @Valid ScheduleDetailPutTimeDto scheduleDetailPutTimeDto
            ) {
        return scheduleDetailService.PutScheduleDetail(ScheduleDetailNo, scheduleDetailPutTimeDto);
    }

    @GetMapping("/detail")
    @Operation(summary = "전체 스케줄표를 조회", description = "해당 여행의 전체 일정을 조회합니다.")
    public ResponseEntity<?> GetScheduleDetailDetail() {
        //is_selected가 true인것들을 시간순서대로 list형태로 response하기
        return null;
    }

    @GetMapping("day-detail")
    @Operation(summary = "일별 스케줄표 조회", description = "선택한 날짜의 전체 스케줄 표를 조회합니다.")
    public ResponseEntity<?> GetScheduleDetailDayDetail() {
        // 시작일을 기준으로 첫째날인지 둘째날인지 입력해서 해당 일자의 일정 list 형태로 response하기
        return null;
    }

    @DeleteMapping
    @Operation(summary = "해당 스케줄 관심없음", description = "해당 스케줄을 삭제합니다.")
    public ResponseEntity<?> DeleteScheduleDetail() {
        // 삭제
        return null;
    }


}


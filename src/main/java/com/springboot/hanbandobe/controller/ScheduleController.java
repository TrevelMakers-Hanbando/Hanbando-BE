package com.springboot.hanbandobe.controller;

import com.springboot.hanbandobe.domain.schedule.dto.ScheduleListResponseDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleRequestDto;
import com.springboot.hanbandobe.domain.schedule.dto.ScheduleResponseDto;
import com.springboot.hanbandobe.domain.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@Tag(name = "Schedule", description = "스케줄 관련 API")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/")
    @Operation(summary = "여행 일정 생성", description = "유저가 여행 일정을 생성합니다.")
    public ResponseEntity<ScheduleResponseDto> PostSchedule(
            @RequestParam Long userNo,
            @RequestBody @Valid ScheduleRequestDto scheduleRequestDto
            ) {

        ScheduleResponseDto dto = scheduleService.PostSchedule(userNo, scheduleRequestDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/list")
    @Operation(summary = "모든 스케줄 목록 조회", description = "유저의 모든 스케줄 목록을 조회합니다.")
    public ResponseEntity<List<ScheduleListResponseDto>> getSchedule(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ScheduleListResponseDto> dto = scheduleService.getSchedule(page, size);
        return ResponseEntity.ok(dto);
    }

    // 특정 스케줄 상세 조회(get)
    @GetMapping("/")
    @Operation(summary = "특정 스케줄 상세 조회", description = "특정 스케줄를 조회합니다.")
    public ResponseEntity<?> getScheduleDetail() {
        return null;
    }

    // 특정 스케줄 정보 전체 수정(put)
    @PutMapping("/")
    @Operation(summary = "특정 스케줄 정보 전체 수정", description = "특정 스케줄 정보를 전체 수정합니다")
    public ResponseEntity<?> PutScheduleDetail() {
        return null;
    }

    // 특정 스케줄 정보 일부 수정(patch)
    @PatchMapping("/")
    @Operation(summary = "특정 스케줄 정보 일부 수정", description = "특정 스케줄 정보를 일부 수정합니다")
    public ResponseEntity<?> PatchScheduleDetail() {
        return null;
    }

    // 특정 스케줄 정보 삭제(delete)
    @DeleteMapping("/")
    @Operation(summary = "특정 스케줄 정보 삭제", description = "특정 스케줄 정보를 삭제합니다.")
    public ResponseEntity<?> DeleteSchedule() {
        return null;
    }
}

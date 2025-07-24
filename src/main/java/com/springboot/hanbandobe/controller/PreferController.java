package com.springboot.hanbandobe.controller;

import com.springboot.hanbandobe.domain.preference.dto.PreferRequestDto;
import com.springboot.hanbandobe.domain.preference.dto.PreferResponseDto;
import com.springboot.hanbandobe.domain.preference.service.PreferService;
import com.springboot.hanbandobe.entity.Prefer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Preference")
@Tag(name = "Preference", description = "선호도 관련 API")
@RequiredArgsConstructor
public class PreferController {
    private final PreferService preferService;

    @PostMapping
    @Operation(summary = "선호도 등록", description = "유저가 선호 유형을 등록합니다.")
    public ResponseEntity<PreferResponseDto> addPrefer(
            @RequestParam Long userNo,
            @RequestBody @Valid PreferRequestDto preferRequestDto
    ) {
        return ResponseEntity.ok(preferService.addPrefer(userNo, preferRequestDto));
    }

    @DeleteMapping
    @Operation(summary = "선호도 삭제", description = "등록한 선호도를 삭제합니다.")
    public ResponseEntity<String> deletePrefer(
            @RequestParam Long userNo
    ) {
        preferService.deletePrefer(userNo);
        return ResponseEntity.ok("등록한 선호도가 삭제되었습니다.");
    }

    @GetMapping
    @Operation(summary = "선호도 조회", description = "등록한 선호도를 조회합니다.")
    public ResponseEntity<PreferResponseDto> getPrefer(
            @RequestParam Long userNo
    ) {
        return ResponseEntity.ok(preferService.getPrefer(userNo));
    }

    @PutMapping
    @Operation(summary = "선호도 전체 수정", description = "등록한 선호도를 모두 수정합니다.")
    public ResponseEntity<PreferResponseDto> updatePrefer(
            @RequestParam Long userNo,
            @RequestBody @Valid PreferRequestDto preferRequestDto
    ) {
        return ResponseEntity.ok(preferService.updatePrefer(userNo));
    }

    @PatchMapping
    @Operation(summary = "선호도 일부 수정", description = "등록한 선호도 일부를 수정합니다.")
    public ResponseEntity<PreferResponseDto> patchPrefer(
            @RequestParam Long userNo,
            @RequestBody @Valid PreferRequestDto preferRequestDto
    ) {
        return ResponseEntity.ok(preferService.patchPrefer(userNo));
    }
}

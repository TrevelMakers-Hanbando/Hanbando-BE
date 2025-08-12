package com.springboot.hanbandobe.controller;

import com.springboot.hanbandobe.domain.alarm.dto.AlarmRequestDto;
import com.springboot.hanbandobe.domain.alarm.dto.AlarmResponseDto;
import com.springboot.hanbandobe.domain.alarm.service.AlarmService;
import com.springboot.hanbandobe.domain.auth.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/Alarm")
@Tag(name = "Alarm", description = "알람 관련 API")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    // 알람 생성
    @PostMapping
    @Operation(summary = "알람 등록", description = "알람을 등록합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<AlarmResponseDto> createAlarm(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody @Valid AlarmRequestDto alarmRequestDto
    ) {
        Long userNo = principalDetails.getUser().getUserNo();

        AlarmResponseDto dto = alarmService.createAlarm(userNo, alarmRequestDto);
        return ResponseEntity.ok(dto);
    }

    // 알람 조회
    @PutMapping
    @Operation(summary = "알람 조회", description = "특정 알람을 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<AlarmResponseDto> putAlarm(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam Long alarmNo
    ) {
        Long userNo = principalDetails.getUser().getUserNo();
        AlarmResponseDto dto = alarmService.putAlarm(userNo, alarmNo);

        return ResponseEntity.ok(dto);
    }

    // 알람 목록
    @GetMapping
    @Operation(summary = "알람 리스트 조회", description = "알람 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<List<AlarmResponseDto>> getBoard(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long userNo = principalDetails.getUser().getUserNo();
        List<AlarmResponseDto> dto = alarmService.getAlarms(userNo, page, size);
        return ResponseEntity.ok(dto);
    }

    // 알람 삭제
    @DeleteMapping
    @Operation(summary = "알람 삭제", description = "알람을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<String> deleteAlarm(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam Long alarmNo
    ) {
        Long userNo = principalDetails.getUser().getUserNo();
        return ResponseEntity.ok(alarmService.deleteAlarm(userNo, alarmNo));
    }
}

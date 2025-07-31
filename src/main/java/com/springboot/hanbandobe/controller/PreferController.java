package com.springboot.hanbandobe.controller;

import com.springboot.hanbandobe.domain.auth.PrincipalDetails;
import com.springboot.hanbandobe.domain.board.dto.BoardResponseDto;
import com.springboot.hanbandobe.domain.preference.dto.PreferRequestDto;
import com.springboot.hanbandobe.domain.preference.dto.PreferResponseDto;
import com.springboot.hanbandobe.domain.preference.service.PreferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
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
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BoardResponseDto.class)
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
    public ResponseEntity<PreferResponseDto> addPrefer(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody @Valid PreferRequestDto preferRequestDto
    ) {
        Long userNo = principalDetails.getUser().getUserNo();

        return ResponseEntity.ok(preferService.addPrefer(userNo, preferRequestDto));
    }

    @DeleteMapping
    @Operation(summary = "선호도 삭제", description = "등록한 선호도를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BoardResponseDto.class)
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
    public ResponseEntity<String> deletePrefer(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long userNo = principalDetails.getUser().getUserNo();

        preferService.deletePrefer(userNo);
        return ResponseEntity.ok("등록한 선호도가 삭제되었습니다.");
    }

    @GetMapping
    @Operation(summary = "선호도 조회", description = "등록한 선호도를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BoardResponseDto.class)
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
    public ResponseEntity<PreferResponseDto> getPrefer(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long userNo = principalDetails.getUser().getUserNo();

        return ResponseEntity.ok(preferService.getPrefer(userNo));
    }

    @PutMapping
    @Operation(summary = "선호도 전체 수정", description = "등록한 선호도를 모두 수정합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BoardResponseDto.class)
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
    public ResponseEntity<PreferResponseDto> updatePrefer(
            @RequestParam Long userNo,
            @RequestBody @Valid PreferRequestDto preferRequestDto
    ) {
        return ResponseEntity.ok(preferService.updatePrefer(userNo, preferRequestDto));
    }

    @PatchMapping
    @Operation(summary = "선호도 일부 수정", description = "등록한 선호도 일부를 수정합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BoardResponseDto.class)
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
    public ResponseEntity<PreferResponseDto> patchPrefer(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody @Valid PreferRequestDto preferRequestDto
    ) {
        Long userNo = principalDetails.getUser().getUserNo();

        return ResponseEntity.ok(preferService.patchPrefer(userNo, preferRequestDto));
    }
}
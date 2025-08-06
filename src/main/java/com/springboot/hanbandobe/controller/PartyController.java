package com.springboot.hanbandobe.controller;


import com.springboot.hanbandobe.domain.auth.PrincipalDetails;
import com.springboot.hanbandobe.domain.board.dto.BoardResponseDto;
import com.springboot.hanbandobe.domain.party.dto.PartyRequestDto;
import com.springboot.hanbandobe.domain.party.dto.PartyResponseDto;
import com.springboot.hanbandobe.domain.party.service.PartyService;
import com.springboot.hanbandobe.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party")
@Tag(name = "Party", description = "파티 관련 API")
public class PartyController {
    private final PartyService partyService;

    @GetMapping()
    @Operation(summary = "파티 목록 조회", description = "전체 파티의 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json")
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
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<List<PartyResponseDto>> getParties (
            @ParameterObject
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = "partyNo") Pageable pageable
            , @RequestParam(required = false, defaultValue = "") String partyName) {

        Page<PartyResponseDto> partyResponseDtos = partyService.getParties(pageable, partyName);

        if (!partyResponseDtos.isEmpty()) {
            return ResponseEntity.ok(partyResponseDtos.getContent());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{partyNo}")
    @Operation(summary = "파티 단건 조회", description = "단건 파티의 정보를 조회한다.")
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
    public ResponseEntity<PartyResponseDto> getParty (@PathVariable Long partyNo) {
        PartyResponseDto partyResponseDto = partyService.getParty(partyNo);

        return ResponseEntity.ok(partyResponseDto);
    }

    @PostMapping()
    @Operation(summary = "파티 추가", description = "파티를 추가한다.")
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
    public ResponseEntity<PartyResponseDto> createParty (
            @AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestBody PartyRequestDto partyRequestDto) {

        User user = principalDetails.getUser();

        partyService.saveParty(user, partyRequestDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{partyNo}")
    @Operation(summary = "파티 수정", description = "파티를 수정한다.")
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
    public ResponseEntity<BoardResponseDto> updateParty (
            @AuthenticationPrincipal PrincipalDetails principalDetails
            , @PathVariable Long partyNo, @RequestBody PartyRequestDto partyRequestDto) {

        User user = principalDetails.getUser();

        partyService.updateParty(partyNo, user, partyRequestDto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{partyNo}")
    @Operation(summary = "파티 삭제", description = "파티를 삭제한다.")
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
    public ResponseEntity<BoardResponseDto> deleteParty (
            @AuthenticationPrincipal PrincipalDetails principalDetails
            , @PathVariable Long partyNo) {

        User user = principalDetails.getUser();

        partyService.deleteParty(partyNo, user);

        return ResponseEntity.noContent().build();
    }
}

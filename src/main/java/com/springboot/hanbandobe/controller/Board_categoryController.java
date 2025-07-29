package com.springboot.hanbandobe.controller;

import com.springboot.hanbandobe.domain.auth.PrincipalDetails;
import com.springboot.hanbandobe.domain.board.dto.BoardResponseDto;
import com.springboot.hanbandobe.domain.board_category.dto.Board_categoryRequestDto;
import com.springboot.hanbandobe.domain.board_category.dto.Board_categoryResponseDto;
import com.springboot.hanbandobe.domain.board_category.service.Board_categoryService;
import com.springboot.hanbandobe.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board-category")
@Tag(name = "Board_category APIs", description = "게시판 카테고리 관련 API")
public class Board_categoryController {
    private final Board_categoryService boardCategoryService;

    @GetMapping()
    @Operation(summary = "게시판 카테고리 목록 조회", description = "전체 게시판 카테고리의 목록을 조회한다.")
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
    public ResponseEntity<List<Board_categoryResponseDto>> getBoard_categories () {
        List<Board_categoryResponseDto> boardCategoryResponseDtos =  boardCategoryService.getBoard_categories();

        if (!boardCategoryResponseDtos.isEmpty()) {
            return ResponseEntity.ok(boardCategoryResponseDtos);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping()
    @Operation(summary = "게시판 카테고리 추가", description = "게시판 카테고리를 추가한다.")
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
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<BoardResponseDto> createBoard_category (
            @AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestBody Board_categoryRequestDto boardCategoryRequestDto) {

        boardCategoryService.saveBoard_category(boardCategoryRequestDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{boardCategoryNo}")
    @Operation(summary = "게시판 카테고리 수정", description = "게시판 카테고리를 수정한다.")
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
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Board_categoryResponseDto> updateBoard_category (
            @PathVariable Long boardCategoryNo
            , @RequestBody Board_categoryRequestDto boardCategoryRequestDto) {

        boardCategoryService.updateBoard_category(boardCategoryNo, boardCategoryRequestDto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{boardCategoryNo}")
    @Operation(summary = "게시판 카테고리 삭제", description = "게시판 카테고리를 삭제한다.")
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
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Board_categoryResponseDto> deleteBoard_category (
            @PathVariable Long boardCategoryNo) {
        boardCategoryService.deleteBoard_category(boardCategoryNo);

        return ResponseEntity.noContent().build();
    }
}

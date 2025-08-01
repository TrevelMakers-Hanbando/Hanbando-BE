package com.springboot.hanbandobe.controller;

import com.springboot.hanbandobe.domain.auth.PrincipalDetails;
import com.springboot.hanbandobe.domain.board.dto.BoardRequestDto;
import com.springboot.hanbandobe.domain.board.dto.BoardResponseDto;
import com.springboot.hanbandobe.domain.board.service.BoardService;
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
@RequestMapping("/api/board")
@Tag(name = "Board", description = "게시판 관련 API")
public class BoardController {
    private final BoardService boardService;

    @GetMapping()
    @Operation(summary = "게시판 목록 조회", description = "전체 게시판의 목록을 조회한다.")
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
    public ResponseEntity<List<BoardResponseDto>> getBoards (
            @ParameterObject
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = "boardNo") Pageable pageable
            , @AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestParam(required = true) Long boardCategoryNo
            , @RequestParam(required = false, defaultValue = "") String boardTitle) {

        User user = (principalDetails != null) ? principalDetails.getUser() : null;

        Page<BoardResponseDto> boardResponseDtos = boardService.getBoards(pageable, user, boardCategoryNo, boardTitle);

        if (!boardResponseDtos.isEmpty()) {
            return ResponseEntity.ok(boardResponseDtos.getContent());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{boardNo}")
    @Operation(summary = "게시판 단건 조회", description = "단건 게시판의 정보를 조회한다.")
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
    public ResponseEntity<BoardResponseDto> getBoard (@PathVariable Long boardNo) {
        BoardResponseDto boardResponseDto = boardService.getBoard(boardNo);

        return ResponseEntity.ok(boardResponseDto);
    }

    @PostMapping()
    @Operation(summary = "게시판 추가", description = "게시판을 추가한다.")
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
    public ResponseEntity<BoardResponseDto> createBoard (
            @AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestBody BoardRequestDto boardRequestDto) {

        User user = principalDetails.getUser();

        boardService.saveBoard(user, boardRequestDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{boardNo}")
    @Operation(summary = "게시판 수정", description = "게시판을 수정한다.")
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
    public ResponseEntity<BoardResponseDto> updateBoard (
            @AuthenticationPrincipal PrincipalDetails principalDetails
            , @PathVariable Long boardNo, @RequestBody BoardRequestDto boardRequestDto) {

        User user = principalDetails.getUser();

        boardService.updateBoard(user, boardNo, boardRequestDto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{boardNo}")
    @Operation(summary = "게시판 삭제", description = "게시판을 삭제한다.")
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
    public ResponseEntity<BoardResponseDto> deleteBoard (
            @AuthenticationPrincipal PrincipalDetails principalDetails
            , @PathVariable Long boardNo) {

        User user = principalDetails.getUser();

        boardService.deleteBoard(user, boardNo);

        return ResponseEntity.noContent().build();
    }
}
package com.springboot.hanbandobe.domain.board.service;

import com.springboot.hanbandobe.domain.board.dto.BoardRequestDto;
import com.springboot.hanbandobe.domain.board.dto.BoardResponseDto;
import com.springboot.hanbandobe.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    void saveBoard(BoardRequestDto boardRequestDto);

    BoardResponseDto getBoard(Long boardNo);

    Page<BoardResponseDto> getBoards(Pageable pageable, Long boardCategoryNo, String title);

    void deleteBoard(Long boardNo);

    void updateBoard(Long boardNo, BoardRequestDto boardRequestDto);
}

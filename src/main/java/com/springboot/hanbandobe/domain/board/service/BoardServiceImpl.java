package com.springboot.hanbandobe.domain.board.service;

import com.springboot.hanbandobe.domain.board.dto.BoardRequestDto;
import com.springboot.hanbandobe.domain.board.dto.BoardResponseDto;
import com.springboot.hanbandobe.domain.board.repository.BoardRepository;
import com.springboot.hanbandobe.domain.board_category.repository.Board_categoryRepository;
import com.springboot.hanbandobe.entity.Board;
import com.springboot.hanbandobe.entity.Board_category;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final Board_categoryRepository board_categoryRepository;

    @Override
    public void saveBoard(BoardRequestDto boardRequestDto) {
        Board_category boardCategory = board_categoryRepository.findById(boardRequestDto.getBoardCategoryNo())
                .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));

        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .boardCategory(boardCategory)
                .build();

        boardRepository.save(board);
    }

    @Override
    public BoardResponseDto getBoard(Long boardNo) {
        BoardResponseDto boardResponseDto =  boardRepository.findById(boardNo)
                .map(BoardResponseDto::new).orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

        return boardResponseDto;
    }

    @Override
    public Page<BoardResponseDto> getBoards(Pageable pageable, Long boardCategoryNo, String boardTitle) {
        Page<BoardResponseDto> boardResponseDtos = boardRepository.findBoardsByBoardCategory_BoardCategoryNoAndTitleContains(pageable, boardCategoryNo, boardTitle).map(BoardResponseDto::new);

        return boardResponseDtos;
    }

    @Transactional
    @Override
    public void updateBoard(Long boardNo, BoardRequestDto boardRequestDto) {
        Board board =  boardRepository.findById(boardNo)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

        Board_category boardCategory = board_categoryRepository.findById(boardRequestDto.getBoardCategoryNo())
                .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));

        board.setBoardCategory(boardCategory);
        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());

        boardRepository.save(board);
    }

    @Transactional
    @Override
    public void deleteBoard(Long boardNo) {
        Board board =  boardRepository.findById(boardNo)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

        board.setIsDeleted(true);
        board.setDeletedAt(LocalDateTime.now());
        board.setUpdatedAt(board.getUpdatedAt());

        boardRepository.save(board);
    }
}

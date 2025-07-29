package com.springboot.hanbandobe.domain.board_category.service;

import com.springboot.hanbandobe.domain.board_category.dto.Board_categoryRequestDto;
import com.springboot.hanbandobe.domain.board_category.dto.Board_categoryResponseDto;
import com.springboot.hanbandobe.domain.board_category.repository.Board_categoryRepository;
import com.springboot.hanbandobe.entity.Board_category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class Board_categoryServiceImpl implements Board_categoryService {
    private final Board_categoryRepository boardCategoryRepository;

    @Override
    public void saveBoard_category(Board_categoryRequestDto boardCategoryRequestDto) {

        Board_category boardCategory = Board_category.builder()
                .name(boardCategoryRequestDto.getName())
                .build();

        boardCategoryRepository.save(boardCategory);
    }

//    @Override
//    public Board_category getBoard_category(Long boardCategoryNo) {
//        Board_category boardCategory = boardCategoryRepository.findById(boardCategoryNo)
//                .map(BoardResponseDto::new).orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
//
//        return null;
//    }

    @Override
    public List<Board_categoryResponseDto> getBoard_categories() {
        List<Board_categoryResponseDto> boardCategoryResponseDtos = boardCategoryRepository.findAll()
                .stream().map(Board_categoryResponseDto::new).collect(Collectors.toList());

        return boardCategoryResponseDtos;
    }

    @Override
    @Transactional
    public void updateBoard_category(Long boardCategoryNo, Board_categoryRequestDto boardCategoryRequestDto) {
        Board_category boardCategory = boardCategoryRepository.findById(boardCategoryNo)
                        .orElseThrow(() -> new RuntimeException("게시판 카테고리를 찾을 수 없습니다."));

        boardCategory.setName(boardCategoryRequestDto.getName());

        boardCategoryRepository.save(boardCategory);
    }

    @Override
    public void deleteBoard_category(Long boardCategoryNo) {
        boardCategoryRepository.deleteById(boardCategoryNo);
    }
}
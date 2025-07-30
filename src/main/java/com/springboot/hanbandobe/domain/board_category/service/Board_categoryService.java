package com.springboot.hanbandobe.domain.board_category.service;

import com.springboot.hanbandobe.domain.board_category.dto.Board_categoryRequestDto;
import com.springboot.hanbandobe.domain.board_category.dto.Board_categoryResponseDto;
import com.springboot.hanbandobe.entity.User;

import java.util.List;

public interface Board_categoryService {

    void saveBoard_category(Board_categoryRequestDto boardCategoryRequestDto);

//    Board_category getBoard_category(Long boardCategoryNo);

    List<Board_categoryResponseDto> getBoard_categories();

    void updateBoard_category(Long boardCategoryNo, Board_categoryRequestDto boardCategoryRequestDto);

    void deleteBoard_category(Long boardCategoryNo);
}

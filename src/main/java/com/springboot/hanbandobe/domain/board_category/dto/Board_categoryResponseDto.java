package com.springboot.hanbandobe.domain.board_category.dto;

import com.springboot.hanbandobe.entity.Board_category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board_categoryResponseDto {
    private Long boardCategoryNo;

    private String name;

    public Board_categoryResponseDto(Board_category boardCategory) {
        this.boardCategoryNo = boardCategory.getBoardCategoryNo();
        this.name = boardCategory.getName();
    }
}

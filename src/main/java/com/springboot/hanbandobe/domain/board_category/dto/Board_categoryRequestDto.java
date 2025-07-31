package com.springboot.hanbandobe.domain.board_category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board_categoryRequestDto {
    private Long boardCategoryNo;

    private String name;
}

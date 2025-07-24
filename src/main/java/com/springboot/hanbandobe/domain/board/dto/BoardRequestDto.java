package com.springboot.hanbandobe.domain.board.dto;

import com.springboot.hanbandobe.entity.Board;
import com.springboot.hanbandobe.entity.Board_category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {
    private Long userNo;

    private String title;

    private String content;

    private Long boardCategoryNo;
}

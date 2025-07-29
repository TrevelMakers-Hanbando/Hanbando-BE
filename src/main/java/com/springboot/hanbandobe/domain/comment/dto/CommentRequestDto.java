package com.springboot.hanbandobe.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private Long commentNo;

    private Long boardNo;

    private String content;

    private Long parentCommentNo;
}

package com.springboot.hanbandobe.domain.comment.service;

import com.springboot.hanbandobe.domain.comment.dto.CommentRequestDto;
import com.springboot.hanbandobe.domain.comment.dto.CommentResponseDto;
import com.springboot.hanbandobe.entity.User;

import java.util.List;

public interface CommentService {
    void saveComment(User user, CommentRequestDto commentRequestDto);

    CommentResponseDto getComment(Long commentNo);

//    List<CommentResponseDto> getComments();

    List<CommentResponseDto> getCommentsByBoardNo(Long boardNo);

    void updateComment(User user, Long commentNo, CommentRequestDto commentRequestDto);

    void deleteComment(User user, Long commentNo);
}

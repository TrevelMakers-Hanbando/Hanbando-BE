package com.springboot.hanbandobe.domain.comment.service;

import com.springboot.hanbandobe.domain.comment.dto.CommentRequestDto;
import com.springboot.hanbandobe.domain.comment.dto.CommentResponseDto;
import com.springboot.hanbandobe.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    void saveComment(User user, Long boardNo, CommentRequestDto commentRequestDto);

    CommentResponseDto getComment(Long commentNo);

//    List<CommentResponseDto> getComments();

    Page<CommentResponseDto> getCommentsByBoardNo(Pageable pageable, Long boardNo);

    void updateComment(User user, Long boardNo, Long commentNo, CommentRequestDto commentRequestDto);

    void deleteComment(User user, Long boardNo, Long commentNo);
}

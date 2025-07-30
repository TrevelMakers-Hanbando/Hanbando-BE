package com.springboot.hanbandobe.domain.comment.service;

import com.springboot.hanbandobe.domain.board.repository.BoardRepository;
import com.springboot.hanbandobe.domain.comment.dto.CommentRequestDto;
import com.springboot.hanbandobe.domain.comment.dto.CommentResponseDto;
import com.springboot.hanbandobe.domain.comment.repository.CommentRepository;
import com.springboot.hanbandobe.entity.Board;
import com.springboot.hanbandobe.entity.Comment;
import com.springboot.hanbandobe.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public void saveComment(User user, Long boardNo, CommentRequestDto commentRequestDto) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new RuntimeException("게시판을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .parentCommentNo(commentRequestDto.getParentCommentNo())
                .board(board)
                .user(user)
                .build();

        commentRepository.save(comment);
    }

    @Override
    public CommentResponseDto getComment(Long commentNo) {
        CommentResponseDto commentResponseDto = commentRepository.findById(commentNo)
                .map(CommentResponseDto::new).orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        return commentResponseDto;
    }

    @Override
    public Page<CommentResponseDto> getCommentsByBoardNo(Pageable pageable, Long boardNo) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new RuntimeException("게시판을 찾을 수 없습니다."));

        Page<CommentResponseDto> commentResponseDtos = commentRepository.findCommentsByBoard_BoardNo(pageable, boardNo)
                .map(CommentResponseDto::new);

        return commentResponseDtos;
    }

    @Transactional
    @Override
    public void updateComment(User user, Long boardNo, Long commentNo, CommentRequestDto commentRequestDto) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if (!(user.getUserNo() == comment.getUser().getUserNo())) {
            throw new RuntimeException("댓글 작성자가 아닙니다.");
        }

        comment.setContent(commentRequestDto.getContent());
    }

    @Override
    public void deleteComment(User user, Long boardNo, Long commentNo) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if (!(user.getUserNo() == comment.getUser().getUserNo())) {
            throw new RuntimeException("댓글 작성자가 아닙니다.");
        }

        commentRepository.deleteById(commentNo);
    }
}

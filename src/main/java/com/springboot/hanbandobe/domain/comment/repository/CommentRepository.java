package com.springboot.hanbandobe.domain.comment.repository;

import com.springboot.hanbandobe.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByBoard_BoardNo(Long boardNo);
}

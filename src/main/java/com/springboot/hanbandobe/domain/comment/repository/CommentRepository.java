package com.springboot.hanbandobe.domain.comment.repository;

import com.springboot.hanbandobe.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findCommentsByBoard_BoardNo(Pageable pageable, Long boardNo);
}

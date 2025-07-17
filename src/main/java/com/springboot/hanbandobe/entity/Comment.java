package com.springboot.hanbandobe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long commentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", nullable = false)
    private Board board;

    @Column(name = "content", length = 100, nullable = false)
    private String content;

    @Column(name = "parent_comment_no", nullable = true)
    private Long parentCommentNo = null;
}

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
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;

@Entity
@Table(name = "alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_no", nullable = false)
    private long alarmNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 100, nullable = false)
    private String content;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = FALSE;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

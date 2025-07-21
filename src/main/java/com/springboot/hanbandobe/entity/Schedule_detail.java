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

import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;

@Entity
@Table(name = "schedule_detail")
public class Schedule_detail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_detail_no", nullable = false)
    private Long scheduleDetailNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_category_no", nullable = false)
    private Travel_category travelCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_no", nullable = false)
    private Schedule schedule;

    @Column(name = "is_selected", nullable = false)
    private Boolean isSelected = FALSE;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at", nullable = false)
    private LocalDateTime endedAt;
}

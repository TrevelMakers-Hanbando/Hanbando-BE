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

import lombok.*;


import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", nullable = false)
    private Long userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_no", nullable = false)
    private Role role;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt = null;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = FALSE;

    @Column(name = "deleted_content", length = 100, nullable = true)
    private String deletedContent = null;
}

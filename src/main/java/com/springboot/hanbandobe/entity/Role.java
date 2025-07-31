package com.springboot.hanbandobe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "role_no", nullable = false)
    private Integer roleNo;

    @Column(name = "name", length = 100, nullable = false)
    private String name;
}

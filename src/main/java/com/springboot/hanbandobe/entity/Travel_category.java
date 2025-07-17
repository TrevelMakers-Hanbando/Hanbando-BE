package com.springboot.hanbandobe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "travel_category")
public class Travel_category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_category_no", nullable = false)
    private Long travelCategoryNo;

    @Column(name = "name", length = 30, nullable = false)
    private String name;
}

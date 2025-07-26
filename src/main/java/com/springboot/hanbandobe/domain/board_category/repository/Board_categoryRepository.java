package com.springboot.hanbandobe.domain.board_category.repository;

import com.springboot.hanbandobe.entity.Board_category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface Board_categoryRepository extends JpaRepository<Board_category, Long> {
}

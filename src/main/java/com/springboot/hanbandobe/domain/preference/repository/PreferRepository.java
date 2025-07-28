package com.springboot.hanbandobe.domain.preference.repository;

import com.springboot.hanbandobe.entity.Prefer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferRepository extends JpaRepository<Prefer, Prefer.PreferId> {
    Optional<Prefer> findByUser_UserNo(Long userNo);
}
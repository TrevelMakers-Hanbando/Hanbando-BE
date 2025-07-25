package com.springboot.hanbandobe.domain.user.repository;

import com.springboot.hanbandobe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

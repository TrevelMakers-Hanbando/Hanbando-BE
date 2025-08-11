package com.springboot.hanbandobe.domain.alarm.repository;

import com.springboot.hanbandobe.entity.Alarm;
import com.springboot.hanbandobe.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Page<Alarm> findByUser(User user, Pageable pageable);
}

package com.springboot.hanbandobe.domain.alarm.repository;

import com.springboot.hanbandobe.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}

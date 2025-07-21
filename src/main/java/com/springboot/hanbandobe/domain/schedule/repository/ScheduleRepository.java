package com.springboot.hanbandobe.domain.schedule.repository;

import com.springboot.hanbandobe.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}

package com.springboot.hanbandobe.domain.schedule.repository;

import com.springboot.hanbandobe.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByUserUserNo(Long userNo);

}

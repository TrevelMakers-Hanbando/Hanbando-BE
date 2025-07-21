package com.springboot.hanbandobe.domain.schedule_detail.repository;

import com.springboot.hanbandobe.entity.Schedule;
import com.springboot.hanbandobe.entity.Schedule_detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleDetailRepository extends JpaRepository<Schedule_detail, Long> {
    @Query("SELECT sd FROM Schedule_detail sd JOIN FETCH sd.travelCategory WHERE sd.schedule.scheduleNo = :scheduleNo")
    List<Schedule_detail> findAllWithCategoryByScheduleNo(@Param("scheduleNo") Long scheduleNo);
}
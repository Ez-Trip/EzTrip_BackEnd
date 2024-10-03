package com.eztrip.repository.schedule;

import com.eztrip.entity.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 필요에 따라 사용자별 일정 검색 메서드를 추가할 수 있음
}
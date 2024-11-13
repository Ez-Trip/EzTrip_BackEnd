package com.eztrip.repository.schedule;

import com.eztrip.entity.member.Member;
import com.eztrip.entity.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByMember(Member member);
}
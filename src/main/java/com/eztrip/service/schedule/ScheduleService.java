package com.eztrip.service.schedule;

import com.eztrip.dto.schedule.ScheduleRequest;
import com.eztrip.entity.member.Member;
import com.eztrip.entity.schedule.Schedule;
import com.eztrip.repository.member.MemberRepository;
import com.eztrip.repository.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Schedule createSchedule(ScheduleRequest scheduleRequest) {
        Member member = memberRepository.findById(scheduleRequest.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다. ID: " + scheduleRequest.getMemberId()));

        Schedule schedule = Schedule.builder()
                .title(scheduleRequest.getTitle())
                .path(scheduleRequest.getPath())
                .date(scheduleRequest.getDate())
                .image(scheduleRequest.getImage())
                .member(member)
                .build();

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. ID: " + id));
    }

    @Transactional
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}
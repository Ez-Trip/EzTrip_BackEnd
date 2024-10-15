package com.eztrip.service.schedule;

import com.eztrip.dto.schedule.ScheduleRequest;
import com.eztrip.entity.member.Member;
import com.eztrip.entity.schedule.PathDetail;
import com.eztrip.entity.schedule.Schedule;
import com.eztrip.repository.member.MemberRepository;
import com.eztrip.repository.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        // Schedule 객체 생성
        Schedule schedule = Schedule.builder()
                .title(scheduleRequest.getTitle())
                .path(scheduleRequest.getPath())
                .date(scheduleRequest.getDate())
                .image(scheduleRequest.getImage())
                .price(scheduleRequest.getPrice())  // 총 금액 설정
                .member(member)
                .build();

        // pathDetails가 null이면 빈 리스트로 초기화
        if (schedule.getPathDetails() == null) {
            schedule.setPathDetails(new ArrayList<>());
        }

        // PathDetail 리스트 추가
        List<PathDetail> pathDetails = new ArrayList<>();
        for (ScheduleRequest.PathDetailRequest detailRequest : scheduleRequest.getPathDetails()) {
            PathDetail pathDetail = PathDetail.builder()
                    .segmentCode(detailRequest.getSegmentCode())
                    .placeName(detailRequest.getPlaceName())
                    .address(detailRequest.getAddress())
                    .price(detailRequest.getPrice())
                    .segmentType(detailRequest.getSegmentType())
                    .schedule(schedule)
                    .build();
            pathDetails.add(pathDetail);
        }

        // Schedule 객체에 PathDetail 추가
        schedule.getPathDetails().addAll(pathDetails);
        schedule.setTotalPrice();  // 총 금액 계산 및 설정
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
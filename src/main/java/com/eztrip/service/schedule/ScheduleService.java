package com.eztrip.service.schedule;

import com.eztrip.entity.member.Member;
import com.eztrip.entity.category.MemberCategory;
import com.eztrip.dto.schedule.ScheduleRequest;
import com.eztrip.entity.schedule.PathDetail;
import com.eztrip.entity.schedule.Schedule;
import com.eztrip.repository.member.MemberRepository;
import com.eztrip.repository.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                .price(scheduleRequest.getPrice())
                .member(member)
                .build();

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

        schedule.setPathDetails(pathDetails);
        schedule.setTotalPrice();
        return scheduleRepository.save(schedule);
    }

    @Transactional(readOnly = true)
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. ID: " + id));
    }

    @Transactional
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 스케줄이 존재하지 않습니다. ID: " + id);
        }
        scheduleRepository.deleteById(id);
    }

    @Transactional
    public Schedule updateSchedule(Long id, ScheduleRequest scheduleRequest) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("업데이트할 스케줄을 찾을 수 없습니다. ID: " + id));

        // 기존 스케줄 정보 업데이트
        existingSchedule.updateSchedule(scheduleRequest);

        // PathDetail 업데이트 처리
        List<PathDetail> existingPathDetails = existingSchedule.getPathDetails();

        // 요청받은 pathDetails를 기존 PathDetail과 비교하여 업데이트
        List<PathDetail> newPathDetails = new ArrayList<>();
        for (ScheduleRequest.PathDetailRequest detailRequest : scheduleRequest.getPathDetails()) {
            boolean pathDetailExists = false;

            // 기존 PathDetail과 비교하여 존재하는 경우 업데이트
            for (PathDetail pathDetail : existingPathDetails) {
                if (pathDetail.getSegmentCode().equals(detailRequest.getSegmentCode())) {
                    pathDetail.setPlaceName(detailRequest.getPlaceName());
                    pathDetail.setAddress(detailRequest.getAddress());
                    pathDetail.setPrice(detailRequest.getPrice());
                    pathDetail.setSegmentType(detailRequest.getSegmentType());
                    newPathDetails.add(pathDetail);
                    pathDetailExists = true;
                    break;
                }
            }

            // 새로운 PathDetail이 추가되는 경우
            if (!pathDetailExists) {
                PathDetail newPathDetail = PathDetail.builder()
                        .segmentCode(detailRequest.getSegmentCode())
                        .placeName(detailRequest.getPlaceName())
                        .address(detailRequest.getAddress())
                        .price(detailRequest.getPrice())
                        .segmentType(detailRequest.getSegmentType())
                        .schedule(existingSchedule)  // 기존 Schedule을 연결
                        .build();
                newPathDetails.add(newPathDetail);
            }
        }

        // 최종적으로 PathDetail 업데이트
        existingSchedule.setPathDetails(newPathDetails);

        // 총 가격 재계산
        existingSchedule.setTotalPrice();

        // 최종 저장
        return scheduleRepository.save(existingSchedule);  // 저장 후 반환
    }

    // **회원의 선호도 정보를 가져오는 메서드 추가**
    @Transactional(readOnly = true)
    public String getMemberPreference(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다. ID: " + memberId));

        // 모든 MemberCategory의 preferenceCode를 연결하여 반환
        return member.getMemberCategories().stream()
                .map(MemberCategory::getPreferenceCode) // A1B2 형식으로 연결
                .collect(Collectors.joining());
    }
}
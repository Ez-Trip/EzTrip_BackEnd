package com.eztrip.controller.schedule;

import com.eztrip.dto.schedule.ScheduleRequest;
import com.eztrip.dto.Recommendation.RecommendationRequest;
import com.eztrip.dto.Recommendation.RecommendationResponse;
import com.eztrip.entity.schedule.Schedule;
import com.eztrip.service.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final RestTemplate restTemplate;  // RestTemplate 주입

    // 스케줄 생성
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Schedule createdSchedule = scheduleService.createSchedule(scheduleRequest);
        return ResponseEntity.ok(createdSchedule);
    }

    // 모든 스케줄 조회
    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    // ID로 스케줄 조회
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    // 스케줄 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    // AI 모델과 통신하여 추천 경로 생성
    @PostMapping("/recommend")
    public ResponseEntity<Schedule> recommendAndCreateSchedule(@RequestBody RecommendationRequest request) {
        String flaskUrl = "http://localhost:3309/recommend";

        // 1. 회원의 선호도 정보 가져오기 (memberId로 검증)
        String preference = scheduleService.getMemberPreference(request.getMemberId());

        // 2. Flask 서버에 보낼 요청 생성 (stationName, totalBudget, preference 포함)
        RecommendationRequest flaskRequest = new RecommendationRequest();
        flaskRequest.setStationName(request.getStationName());
        flaskRequest.setTotalBudget(request.getTotalBudget());
        flaskRequest.setPreference(preference);
        flaskRequest.setMemberId(request.getMemberId());

        // 3. Flask 서버로 POST 요청 보내기
        ResponseEntity<RecommendationResponse> response = restTemplate.postForEntity(
                flaskUrl, flaskRequest, RecommendationResponse.class);

        RecommendationResponse recommendation = response.getBody();

        // 4. 추천받은 데이터를 바탕으로 스케줄 생성 요청 객체 생성
        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.setTitle("Recommended Course");
        scheduleRequest.setPath(request.getStationName());
        scheduleRequest.setPrice(recommendation.getTotalPrice());
        scheduleRequest.setMemberId(request.getMemberId());
        scheduleRequest.setPathDetails(recommendation.toPathDetailRequests());

        // 5. 스케줄 생성 및 응답
        Schedule createdSchedule = scheduleService.createSchedule(scheduleRequest);
        return ResponseEntity.ok(createdSchedule);
    }
}
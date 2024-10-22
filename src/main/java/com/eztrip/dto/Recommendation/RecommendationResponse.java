package com.eztrip.dto.Recommendation;

import com.eztrip.dto.schedule.ScheduleRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecommendationResponse {
    private String courseDetail; // 추천된 경로 세부 정보
    private int totalPrice;      // 총 가격

    // ScheduleRequest.PathDetailRequest 리스트로 변환하는 메서드
    public List<ScheduleRequest.PathDetailRequest> toPathDetailRequests() {
        List<ScheduleRequest.PathDetailRequest> pathDetails = new ArrayList<>();

        // 예제: courseDetail 문자열을 파싱하여 PathDetailRequest 객체 생성
        String[] segments = courseDetail.split(", ");
        for (String segment : segments) {
            ScheduleRequest.PathDetailRequest detail = new ScheduleRequest.PathDetailRequest();
            detail.setPlaceName(segment);  // 세그먼트 이름을 장소로 설정
            detail.setPrice(10000);  // 예제 금액 (더미 데이터)
            detail.setSegmentType("General"); // 세그먼트 유형 (예: 음식, 숙박 등)
            pathDetails.add(detail);
        }

        return pathDetails;
    }
}
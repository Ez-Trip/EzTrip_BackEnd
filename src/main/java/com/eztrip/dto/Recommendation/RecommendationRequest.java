package com.eztrip.dto.Recommendation;

import lombok.Data;

@Data
public class RecommendationRequest {
    private String stationName;  // 역 이름
    private int totalBudget;     // 총 예산
    private String preference;   // 사용자 선호도 (A1B2C1D1E1 등)
    private Long memberId;       // 회원 ID
}
package com.eztrip.entity.sns;

import com.eztrip.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 고유 ID

    private String image;  // 공유된 이미지 (URL 또는 경로)

    /**
     * 데이트 코스 형식: "A1B1C1D1E1"
     * A: 음식 종류 (A1: 한식, A2: 일식, A3: 양식, A4: 중식)
     * B: 숙박 (B1: 호텔, B2: 모텔, B3: 게스트하우스)
     * C: 카페 (C1: 카페)
     * D: 문화 (D1: 문화시설)
     * E: 관광 (E1: 관광시설)
     */
    private String dateCourse;  // 데이트코스 (문자열)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 회원 고유 ID와의 관계 설정

    // 엔티티 간 연관관계 메서드 추가
    public void setMember(Member member) {
        this.member = member;
    }
}
package com.eztrip.entity.schedule;

import com.eztrip.entity.member.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 아이디 (기본키)

    private String title;  // 제목

    private String path;  // 경로 (장소나 목적지)

    private LocalDate date;  // 날짜 (java.time.LocalDate 사용)

    private String image;  // 이미지 (URL 또는 경로)

    private int price;  // 총 가격

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference  // 순환 참조 방지
    private Member member;  // 일정의 사용자 (다대일 관계)

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @JsonManagedReference  // 순환 참조 방지
    private List<PathDetail> pathDetails = new ArrayList<>();  // 경로 상세 정보, 빈 리스트로 초기화

    public void setMember(Member member) {
        this.member = member;
    }

    // pathDetails 리스트 설정 메서드 추가
    public void setPathDetails(List<PathDetail> pathDetails) {
        this.pathDetails = pathDetails;
    }

    // 총 금액을 계산하는 메서드 추가
    public void setTotalPrice() {
        this.price = this.pathDetails.stream().mapToInt(PathDetail::getPrice).sum();
    }
}
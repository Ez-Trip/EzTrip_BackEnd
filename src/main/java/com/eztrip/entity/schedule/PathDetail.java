package com.eztrip.entity.schedule;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class PathDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "segment_code")
    private String segmentCode;  // 경로 세그먼트 코드 (A1, B2 등)

    @Column(name = "place_name")
    private String placeName;    // 장소 이름

    private String address;      // 주소

    private int price;           // 가격

    @Column(name = "segment_type")
    private String segmentType;  // 세그먼트 유형 (예: 음식점, 숙박업소 등)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    @JsonBackReference  // 순환 참조 방지
    private Schedule schedule;

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
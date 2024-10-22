package com.eztrip.entity.schedule;

import com.eztrip.dto.schedule.ScheduleRequest;
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
    private Long id;

    private String title;
    private String path;
    private LocalDate date;
    private String image;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference
    private Member member;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PathDetail> pathDetails = new ArrayList<>();  // 빈 리스트로 초기화

    public void setMember(Member member) {
        this.member = member;
    }

    public void setPathDetails(List<PathDetail> pathDetails) {
        if (this.pathDetails == null) {
            this.pathDetails = new ArrayList<>();  // null 방지 초기화
        }
        this.pathDetails.clear();  // 기존 리스트 비우기
        if (pathDetails != null) {
            this.pathDetails.addAll(pathDetails);  // 새로운 경로 추가
        }
    }

    public void setTotalPrice() {
        this.price = this.pathDetails.stream()
                .mapToInt(PathDetail::getPrice)
                .sum();
    }

    public void updateSchedule(ScheduleRequest request) {
        this.title = request.getTitle();
        this.path = request.getPath();
        this.date = request.getDate();
        this.image = request.getImage();
        this.price = request.getPrice();

        List<PathDetail> newPathDetails = new ArrayList<>();
        for (ScheduleRequest.PathDetailRequest detailRequest : request.getPathDetails()) {
            PathDetail pathDetail = PathDetail.builder()
                    .segmentCode(detailRequest.getSegmentCode())
                    .placeName(detailRequest.getPlaceName())
                    .address(detailRequest.getAddress())
                    .price(detailRequest.getPrice())
                    .segmentType(detailRequest.getSegmentType())
                    .schedule(this)  // 양방향 매핑 설정
                    .build();
            newPathDetails.add(pathDetail);
        }
        setPathDetails(newPathDetails);  // 경로 설정
        setTotalPrice();  // 총 금액 재계산
    }
}
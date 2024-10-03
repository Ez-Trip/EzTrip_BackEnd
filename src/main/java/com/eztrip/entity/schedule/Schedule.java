package com.eztrip.entity.schedule;

import com.eztrip.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 일정의 사용자 (다대일 관계)

    public void setMember(Member member) {
        this.member = member;
    }
}
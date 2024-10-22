package com.eztrip.entity.sns;

import com.eztrip.entity.member.Member;
import com.eztrip.entity.schedule.Schedule;
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

    private String dateCourse;  // 데이트코스 (문자열)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 회원과의 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")  // Schedule과의 관계 설정
    private Schedule schedule;  // 연관된 스케줄

    public void setMember(Member member) {
        this.member = member;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
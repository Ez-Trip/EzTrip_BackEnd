package com.eztrip.dto.sns;

import com.eztrip.entity.sns.SnsPost;

public class SnsPostDto {
    private Long id;
    private String image;
    private String dateCourse;
    private Long memberId;
    private Long scheduleId;

    // SnsPost 객체를 매개변수로 받는 생성자
    public SnsPostDto(SnsPost post) {
        this.id = post.getId();
        this.memberId = post.getMember().getId();
        this.image = post.getImage();
        this.dateCourse = post.getDateCourse();
        this.scheduleId = (post.getSchedule() != null) ? post.getSchedule().getId() : null;  // scheduleId가 null일 경우 처리
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getDateCourse() {
        return dateCourse;
    }

    public Long getScheduleId() {
        return scheduleId;
    }
}
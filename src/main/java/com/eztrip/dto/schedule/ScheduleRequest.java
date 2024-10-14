package com.eztrip.dto.schedule;

import java.time.LocalDate;

public class ScheduleRequest {
    private String title;       // 스케줄 제목
    private String path;        // 경로 (장소나 목적지)
    private LocalDate date;     // 날짜
    private String image;       // 이미지 경로 (URL)
    private Long memberId;      // 사용자 ID (memberId)

    // 기본 생성자
    public ScheduleRequest() {}

    // 모든 필드를 사용하는 생성자
    public ScheduleRequest(String title, String path, LocalDate date, String image, Long memberId) {
        this.title = title;
        this.path = path;
        this.date = date;
        this.image = image;
        this.memberId = memberId;
    }

    // Getter와 Setter 메서드
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
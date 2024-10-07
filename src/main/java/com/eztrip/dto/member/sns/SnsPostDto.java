package com.eztrip.dto.member.sns;

import com.eztrip.entity.sns.SnsPost;

public class SnsPostDto {
    private Long id;
    private String image;
    private String dateCourse;

    // SnsPost 객체를 매개변수로 받는 생성자
    public SnsPostDto(SnsPost snsPost) {
        this.id = snsPost.getId();
        this.image = snsPost.getImage();
        this.dateCourse = snsPost.getDateCourse();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getDateCourse() {
        return dateCourse;
    }
}
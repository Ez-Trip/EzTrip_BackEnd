package com.eztrip.dto.member.sns;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnsPostRequest {
    private Long memberId;
    private String image;
    private String dateCourse;
}
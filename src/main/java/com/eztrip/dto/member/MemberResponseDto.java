package com.eztrip.dto.member;

import com.eztrip.dto.category.MemberCategoryDto;
import com.eztrip.dto.schedule.ScheduleDto;
import com.eztrip.dto.sns.SnsPostDto;
import com.eztrip.entity.member.Member;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberResponseDto {
    private Long id;
    private String username;
    private String email;
    private String nickname;
    private String phoneNumber;
    private String image;
    private String birth;
    private String gender;
    private Integer age;
    private Boolean push;
    private Boolean information;
    private String name;
    private String role;
    private List<MemberCategoryDto> preferences; // 선호도 정보를 DTO로 변환
    private List<SnsPostDto> snsPosts;           // SNS 게시물 정보를 DTO로 변환
    private List<ScheduleDto> schedules;         // 스케줄 정보를 DTO로 변환

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.phoneNumber = member.getPhoneNumber();
        this.image = member.getImage();
        this.birth = member.getBirth();
        this.gender = member.getGender();
        this.age = member.getAge();
        this.push = member.getPush();
        this.information = member.getInformation();
        this.name = member.getName();
        this.role = member.getRole().name();
        this.preferences = member.getMemberCategories().stream()
                .map(MemberCategoryDto::new)
                .collect(Collectors.toList());
        this.snsPosts = member.getSnsPosts().stream()
                .map(SnsPostDto::new)
                .collect(Collectors.toList());
        this.schedules = member.getSchedules().stream()
                .map(ScheduleDto::new)
                .collect(Collectors.toList());
    }
}
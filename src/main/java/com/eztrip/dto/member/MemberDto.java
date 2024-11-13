package com.eztrip.dto.member;

import com.eztrip.entity.category.MemberCategory;
import com.eztrip.entity.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

public class MemberDto {

    @Data
    public static class Join {

        private String username;

        private String email;

        private String password;

        private String nickname;

        private String phoneNumber;

        private String image;

        private String birth;

        private String gender;

        private Integer age;

        private Boolean push;

        private Boolean information;

        private String categories;

        private String name;
    }

    @Data
    public static class Login{

        private String username;

        private String password;
    }

    @Data
    public static class PasswordReset {
        private String username;
        private String email;
        private String newPassword;
    }

    @Data
    public static class FindRequest {
        private String username;
        private String email;
    }

    @Data
    public static class UpdateCategories {
        private String categories;
    }

    @Data
    public static class MemberInfoResponse {
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
        private String categories;

        public MemberInfoResponse(Member member, List<MemberCategory> preferences) {
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
            // 현재의 선호도 카테고리를 categories에 할당
            this.categories = member.getMemberCategories().stream()
                    .map(memberCategory -> memberCategory.getCategory().getId())
                    .collect(Collectors.joining());
        }
    }

    // 개인정보 수정
    @Data
    public static class UpdateInfo {
        private String username;
        private String email;
        private String nickname;
        private String phoneNumber;
        private String image;
        private String birth;
        private String gender;
        private Integer age;
        private String name;

        // MemberUpdate로 변환하는 메서드 추가
        public MemberUpdate toMemberUpdate() {
            return new MemberUpdate(
                    username,
                    email,
                    null,  // password는 업데이트하지 않으므로 null로 설정
                    nickname,
                    phoneNumber,
                    image,
                    birth,
                    gender,
                    age,
                    name
            );
        }
    }
}

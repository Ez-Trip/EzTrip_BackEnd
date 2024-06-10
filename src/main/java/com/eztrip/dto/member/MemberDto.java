package com.eztrip.dto.member;

import lombok.Data;

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
    }
}

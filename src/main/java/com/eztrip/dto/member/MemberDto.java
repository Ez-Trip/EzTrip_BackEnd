package com.eztrip.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
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
}

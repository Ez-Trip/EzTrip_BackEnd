package com.eztrip.global.token;

import com.eztrip.entity.member.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenDto {

    private Long id;

    private String username;

    private Role role;

    private String nickname;

    private String memberType;

    private String profile;

    private String grantType;

    private String accessToken;
    private  String name;
    private  String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date accessTokenExpireTime;

    private String refreshToken;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date refreshTokenExpireTime;

    @Override
    public String toString() {
        return "JwtTokenDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", role=" + role +
                ", grantType='" + grantType + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", accessTokenExpireTime=" + accessTokenExpireTime +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshTokenExpireTime=" + refreshTokenExpireTime +
                '}';
    }
}

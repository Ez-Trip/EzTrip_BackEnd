package com.eztrip.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String email;

    private String password;

    private String nickname;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String username, String email, String password, String nickname, String phoneNumber, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Member hashPassword(PasswordEncoder encoder){
        this.password = encoder.encode(this.password);
        return this;
    }

    public boolean checkPassword(String plainPassword, PasswordEncoder encoder){
        return encoder.matches(plainPassword, this.password);
    }
}

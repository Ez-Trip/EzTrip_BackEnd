package com.eztrip.entity.member;

import com.eztrip.entity.category.MemberCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor // 인자값이 없는 디폴트 생성자 선언
@AllArgsConstructor // 전체 필드를 인자로 갖는 생성자 선언
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // 식별자

    private String username; // 우리가 로그인 할 때 쓰는 ID

    private String email; // email

    private String password; // 비밀번호

    private String nickname; // 닉네임

    private String phoneNumber; // 전화번호

    private String image; // 프로필 사진

    private String birth; // 생년월일 (문자열)

    private String gender; // 성별 TODO : Enum 타입 고려

    private Integer age; // 나이 (정수값)

    private Boolean push; // 푸쉬 동의

    private Boolean information; // 정보 제공 동의

    @Enumerated(EnumType.STRING)
    private Role role; // 역할

    private String refreshToken; // 리프레시 토큰

    private LocalDateTime tokenExpirationTime; // 토큰 만료 시간

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MemberCategory> memberCategories = new ArrayList<>();

    @Builder
    public Member(String username, String email, String password, String nickname, String phoneNumber, String image, String birth, String gender, Integer age, Role role, Boolean push, Boolean information) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.birth = birth;
        this.gender = gender;
        this.age = age;
        this.role = role;
        this.push = push;
        this.information = information;
    }

    public Member hashPassword(PasswordEncoder encoder){
        this.password = encoder.encode(this.password);
        return this;
    }

    public boolean checkPassword(String plainPassword, PasswordEncoder encoder){
        return encoder.matches(plainPassword, this.password);
    }

    public void logout() {

        this.tokenExpirationTime = LocalDateTime.now();
    }
}

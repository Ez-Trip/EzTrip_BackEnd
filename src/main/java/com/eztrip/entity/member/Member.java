package com.eztrip.entity.member;

import com.eztrip.dto.member.MemberUpdate;
import com.eztrip.entity.category.MemberCategory;
import com.eztrip.entity.schedule.Schedule;
import com.eztrip.entity.sns.SnsPost;
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
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

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

    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;
    private LocalDateTime tokenExpirationTime;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MemberCategory> memberCategories = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SnsPost> snsPosts = new ArrayList<>(); // SnsPost와의 양방향 매핑 추가

    @Builder
    public Member(String username, String email, String password, String nickname, String phoneNumber, String image,
                  String birth, String gender, Integer age, Role role, Boolean push, Boolean information) {
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

    public Member hashPassword(PasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
        return this;
    }

    public boolean checkPassword(String plainPassword, PasswordEncoder encoder) {
        return encoder.matches(plainPassword, this.password);
    }

    public void logout() {
        this.tokenExpirationTime = LocalDateTime.now();
    }

    public void update(MemberUpdate updateDto) {
        this.username = updateDto.username();
        this.email = updateDto.email();
        this.password = updateDto.password();
        this.nickname = updateDto.nickname();
        this.phoneNumber = updateDto.phoneNumber();
        this.image = updateDto.image();
        this.birth = updateDto.birth();
        this.gender = updateDto.gender();
        this.age = updateDto.age();
    }

    public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setMember(this);
    }

    // SNS 포스트 추가 메서드
    public void addSnsPost(SnsPost snsPost) {
        this.snsPosts.add(snsPost);
        snsPost.setMember(this);
    }

    // 역할 변경 메서드
    public void setRole(Role role) {
        this.role = role;
    }
}
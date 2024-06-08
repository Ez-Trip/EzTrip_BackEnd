package com.eztrip.service.member;

import com.eztrip.dto.member.MemberDto;
import com.eztrip.entity.member.Member;
import com.eztrip.entity.member.Role;
import com.eztrip.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder encoder;

    private void validateEmail(String email) {

        if(memberRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException(); // TODO: ErrorCode 적용
        }
    }

    @Transactional
    public void join(MemberDto.Join dto) {

        validateEmail(dto.getEmail());

        Member joinMember = Member.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .phoneNumber(dto.getPhoneNumber())
                .role(Role.USER)
                .build();

        joinMember.hashPassword(encoder); // 비밀번호 암호화

        memberRepository.save(joinMember);
    }

    public Member findByUsernameAndPassword(String username, String password) {

        Member findMember = memberRepository.findByUsernameAndPassword(username, password)
                .orElseThrow();

        return findMember;
    }
}

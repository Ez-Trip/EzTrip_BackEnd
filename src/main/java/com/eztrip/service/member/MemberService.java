package com.eztrip.service.member;

import com.eztrip.dto.member.MemberDto;
import com.eztrip.entity.member.Member;
import com.eztrip.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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
                .build();

        memberRepository.save(joinMember);
    }
}

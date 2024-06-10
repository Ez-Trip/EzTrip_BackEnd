package com.eztrip.service.member;

import com.eztrip.dto.member.MemberDto;
import com.eztrip.entity.category.Category;
import com.eztrip.entity.member.Member;
import com.eztrip.entity.member.Role;
import com.eztrip.global.error.ErrorCode;
import com.eztrip.global.error.exception.EntityNotFoundException;
import com.eztrip.repository.category.CategoryRepository;
import com.eztrip.repository.member.MemberRepository;
import com.eztrip.service.category.MemberCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberCategoryService memberCategoryService;

    private final CategoryRepository categoryRepository;

    private final PasswordEncoder encoder;

    // 이메일 중복 체크 로직
    private void validateEmail(String email) {

        if(memberRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException(); // TODO: ErrorCode 적용
        }
    }

    // 회원가입 로직
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

        String[] categories = dto.getCategories().split("(?<=\\d)(?=\\D)");

        // 카테고리 연관관계 등록
        for (int i=0; i<categories.length; i++) {

            Category category = categoryRepository.findById(categories[i])
                    .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_CATEGORY));

            memberCategoryService.createMemberCategory(joinMember, category, i+1);
        }

        memberRepository.save(joinMember);
    }

    // 식별자로 확인
    public Member findById(Long id){

        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));

        return findMember;
    }

    // 아이디와 비밀번호로 확인
    public Member findByUsernameAndPassword(String username, String password) {

        Member findMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));

        if (!findMember.checkPassword(password, encoder))
            throw new EntityNotFoundException(ErrorCode.NOT_EQUAL_PASSWORD);

        return findMember;
    }

    // 회원 탈퇴
    public void deleteMember(Long id) {

        memberRepository.deleteById(id);
    }
}

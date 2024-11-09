package com.eztrip.service.member;

import com.eztrip.dto.member.MemberDto;
import com.eztrip.dto.member.MemberUpdate;
import com.eztrip.entity.category.Category;
import com.eztrip.entity.member.Member;
import com.eztrip.entity.member.Role;
import com.eztrip.global.error.ErrorCode;
import com.eztrip.global.error.exception.BusinessException;
import com.eztrip.global.error.exception.EntityNotFoundException;
import com.eztrip.global.token.JwtTokenDto;
import com.eztrip.global.token.TokenManager;
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

    // 자바에서 final 키워드가 달린 필드는 생성자에서 값을 넣어주지 않으면 에러를 발생시킨다
    private final MemberRepository memberRepository;

    private final MemberCategoryService memberCategoryService;

    private final CategoryRepository categoryRepository;

    private final TokenManager tokenManager;

    private final PasswordEncoder encoder;

    // 이메일 중복 체크 로직
    private void validateEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    // 회원가입 로직
    @Transactional
    public void join(MemberDto.Join dto) {

        validateEmail(dto.getEmail());
        validateDuplicate(dto);  // 중복 검사 추가

        Member joinMember = Member.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .phoneNumber(dto.getPhoneNumber())
                .image(dto.getImage())
                .birth(dto.getBirth())
                .gender(dto.getGender())
                .age(dto.getAge())
                .role(Role.ADMIN) // 나중에 USER로 바꾸기
                .push(dto.getPush())
                .information(dto.getInformation())
                .name(dto.getName())
                .build();

        joinMember.hashPassword(encoder); // 비밀번호 암호화

        // 카테고리가 선호도에 따라서 순차적으로 입려된 하나의 문자열로 전달
        // 숫자를 기준으로 잘림 (A1B1C1 -> {"A1", "B1", "C1"})
        String[] categories = dto.getCategories().split("(?<=\\d)(?=\\D)");

        // 카테고리 연관관계 등록
        for (int i=0; i<categories.length; i++) {

            Category category = categoryRepository.findById(categories[i])
                    .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_CATEGORY));

            memberCategoryService.createMemberCategory(joinMember, category, i+1);
        }
        // 카테고리 디비에서 조회 후 저장하는 부분
        memberRepository.save(joinMember);
    }

    // 로그인
    @Transactional
    public JwtTokenDto login(String username, String password) {

        Member loginMember = findByUsernameAndPassword(username, password);

        JwtTokenDto token = tokenManager.createJwtTokenDto(loginMember.getId(), loginMember.getUsername(), loginMember.getNickname(), loginMember.getRole(), loginMember.getName(), loginMember.getImage());

        return token;
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

    // 로그아웃 (refresh token 만료)
    @Transactional
    public void logout(Long id) {

        Member findMember = findById(id);

        findMember.logout();
    }

    // 회원 탈퇴
    @Transactional
    public void deleteMember(Long id) {

        memberRepository.deleteById(id);
    }

    // ID 찾기
    @Transactional
    public void update(Long id, MemberUpdate updateDto) {

        Member findMember = findById(id);

        findMember.update(updateDto);
    }

    // PW 재설정
    @Transactional
    public void resetPassword(String username, String email, String newPassword) {
        // 사용자 조회
        Member member = memberRepository.findByUsernameAndEmail(username, email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));

        // 새 비밀번호 설정 (암호화 포함)
        member.setPassword(encoder.encode(newPassword));
        memberRepository.save(member);
    }

    // 회원가입 중복확인
    private void validateDuplicate(MemberDto.Join dto) {
        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_USERNAME);
        }
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }
        if (memberRepository.findByEmail(dto.getNickname()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    // 중복검사 버튼 API
    // 아이디
    @Transactional
    public boolean isUsernameDuplicate(String username) {
        return memberRepository.findByUsername(username).isPresent();
    }
    // 닉네임
    @Transactional
    public boolean isNicknameDuplicate(String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }
    //이메일
    @Transactional
    public boolean isEmailDuplicate(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    // 아이디와 이메일로 확인
    @Transactional
    public Member findByUsernameAndEmail(String username, String email) {

        Member findMember = memberRepository.findByUsernameAndEmail(username,email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));

        return findMember;
    }

    // Token으로 id 확인 후 조회하는 부분
    public Member getMyInfo(String token) {
        String accessToken = token.substring(7);  // "Bearer " 제거
        Long userId = tokenManager.getUserIdFromToken(accessToken);
        return findById(userId);
    }
}

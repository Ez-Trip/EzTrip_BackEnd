package com.eztrip.controller.member;

import com.eztrip.dto.member.MemberDto;
import com.eztrip.dto.member.MemberUpdate;
import com.eztrip.entity.member.Member;
import com.eztrip.global.token.JwtTokenDto;
import com.eztrip.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/eztrip")
@RequiredArgsConstructor
@Tag(name = "eztrip", description = "설명.")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 API
     */
    @PostMapping("/join")
    @Tag(name = "eztrip")
    @Operation(summary = "join", description = "회원가입")
    public ResponseEntity<?> joinMember(@RequestBody MemberDto.Join dto) {
        memberService.join(dto);

        // 응답 메시지를 조금 더 상세하게 반환
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "User " + dto.getUsername() + " has been registered successfully.");
        response.put("data", dto);

        return ResponseEntity.ok(response);
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto.Login dto) {

        JwtTokenDto token = memberService.login(dto.getUsername(), dto.getPassword());

        return ResponseEntity.ok(token);
    }

    /**
     * 로그아웃 API
     */
    @PostMapping("/logout/{id}")
    public ResponseEntity<?> logout(@RequestParam("id") Long id) {

        memberService.logout(id);

        return ResponseEntity.ok("Logout Success");
    }

    /**
     * 회원 정보 수정 API
     */
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody MemberUpdate updateDto,
                                    @PathVariable("id") Long id) {

        memberService.update(id, updateDto);

        return ResponseEntity.ok("success");
    }

    /**
     * 비밀번호 재설정 API
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody MemberDto.PasswordReset dto) {
        memberService.resetPassword(dto.getUsername(), dto.getEmail(), dto.getNewPassword());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Password has been reset successfully.");

        return ResponseEntity.ok(response);
    }

    /**
     * 중복검사 버튼 API
     */
    @PostMapping("/check-username")
    public ResponseEntity<Void> checkUsernameDuplicate(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        if (memberService.isUsernameDuplicate(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 중복일 경우 409 반환
        }
        return ResponseEntity.ok().build(); // 중복이 아닐 경우 200 반환
    }

    @PostMapping("/check-nickname")
    public ResponseEntity<Void> checkNicknameDuplicate(@RequestBody Map<String, String> request) {
        String nickname = request.get("nickname");
        if (memberService.isNicknameDuplicate(nickname)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 중복일 경우 409 반환
        }
        return ResponseEntity.ok().build(); // 중복이 아닐 경우 200 반환
    }

    @PostMapping("/check-email")
    public ResponseEntity<Void> checkEmailDuplicate(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (memberService.isEmailDuplicate(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 중복일 경우 409 반환
        }
        return ResponseEntity.ok().build(); // 중복이 아닐 경우 200 반환
    }

    /**
     * 아이디와 이메일로 회원 조회 API
     */
    @PostMapping("/findMember")
    public ResponseEntity<Member> findMemberByUsernameAndEmail(
            @RequestBody MemberDto.FindRequest request) {

        Member member = memberService.findByUsernameAndEmail(request.getUsername(), request.getEmail());
        return ResponseEntity.ok(member);
    }

    /**
     * 자신의 모든 등록 정보를 가져오는 API
     */
    @GetMapping("/my-info")
    @Operation(summary = "내 정보 조회", description = "accessToken을 사용하여 자신의 모든 등록 정보를 가져옵니다.")
    public ResponseEntity<MemberDto.MemberInfoResponse> getMyInfo(@RequestHeader("Authorization") String token) {
        MemberDto.MemberInfoResponse memberInfo = memberService.getMyInfo(token);
        return ResponseEntity.ok(memberInfo);
    }


    /**
     * 회원의 선호도 (categories) 수정 API
     */
    @PatchMapping("/update-categories")
    @Operation(summary = "선호도 수정", description = "accessToken을 사용하여 회원의 선호도를 수정합니다.")
    public ResponseEntity<String> updateCategories(
            @RequestHeader("Authorization") String token,
            @RequestBody MemberDto.UpdateCategories request) {

        memberService.updateCategories(token, request.getCategories());
        return ResponseEntity.ok("Categories updated successfully.");
    }
}

package com.eztrip.controller.member;

import com.eztrip.dto.member.MemberDto;
import com.eztrip.dto.member.MemberUpdate;
import com.eztrip.global.token.JwtTokenDto;
import com.eztrip.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
}

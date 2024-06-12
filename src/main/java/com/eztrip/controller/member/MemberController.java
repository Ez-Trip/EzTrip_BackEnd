package com.eztrip.controller.member;

import com.eztrip.dto.member.MemberDto;
import com.eztrip.global.token.JwtTokenDto;
import com.eztrip.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eztrip")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 API
     */
    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@RequestBody MemberDto.Join dto) {

        memberService.join(dto);

        return ResponseEntity.ok(dto.getUsername());
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
}

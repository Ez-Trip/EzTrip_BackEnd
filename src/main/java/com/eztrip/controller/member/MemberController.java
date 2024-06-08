package com.eztrip.controller.member;

import com.eztrip.dto.member.MemberDto;
import com.eztrip.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

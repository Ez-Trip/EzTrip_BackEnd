package com.eztrip.dto.member;

import lombok.Data;

public record MemberUpdate(String username,
                           String email,
                           String password,
                           String nickname,
                           String phoneNumber,
                           String image,
                           String birth,
                           String gender,
                           Integer age
) { }

package com.eztrip.entity.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum Role {

    ADMIN("ADMIN"), USER("USER");

    private final String roleType;

    public static Role from(String role) {

        return Role.valueOf(role);
    }
}

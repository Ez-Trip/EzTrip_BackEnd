package com.eztrip.entity.category;

import com.eztrip.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @Column(name = "category_id")
    private String id; // 식별자 : 자동생성하는 것이 아니라 기존에 등록한 식별자 (A1, B1, C1, ...)

    private String title; // 이름


    // 새로 추가한 부분
    public Category(String id, String title) {
        this.id = id;
        this.title = title;
    }



    // mappedBy = "category"로 수정 (MemberCategory에서 Category를 가리키는 필드)
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MemberCategory> memberCategories = new ArrayList<>();
}
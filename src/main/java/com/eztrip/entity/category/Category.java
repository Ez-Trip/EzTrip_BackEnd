package com.eztrip.entity.category;

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
    private String id; // 식별자 (A1, B1, C1, ...)

    private String title; // 카테고리 이름

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MemberCategory> memberCategories = new ArrayList<>();

    // 새롭게 추가된 두 개의 인자를 받는 생성자
    public Category(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
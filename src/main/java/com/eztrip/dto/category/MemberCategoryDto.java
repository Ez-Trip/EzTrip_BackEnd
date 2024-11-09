package com.eztrip.dto.category;

import com.eztrip.entity.category.MemberCategory;
import lombok.Data;

@Data
public class MemberCategoryDto {
    private String categoryTitle;
    private Integer preference;

    public MemberCategoryDto(MemberCategory memberCategory) {
        this.categoryTitle = memberCategory.getCategory().getTitle(); // Category 엔터티의 title 필드를 참조
        this.preference = memberCategory.getPreference();
    }
}
package com.eztrip.initializer;  // 패키지 선언

import com.eztrip.entity.category.Category;
import com.eztrip.repository.category.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

// 카테고리 디비에서 조회 후 저장하는 부분
@Component
@RequiredArgsConstructor
public class CategoryInitializer {

    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        List<Category> categories = Arrays.asList(
                new Category("A1", "Category A1"), //한식
                new Category("A2", "Category A2"), //중식
                new Category("A3", "Category A3"), //일식
                new Category("A4", "Category A4"), //양식
                new Category("B1", "Category B1"), //호텔
                new Category("B2", "Category B2"), //모텔
                new Category("B3", "Category B3"), //게스트하우스
                new Category("C1", "Category C1"), //카페
                new Category("D1", "Category D1"), //문화시설
                new Category("E1", "Category E1") //관광시설
        );

        for (Category category : categories) {
            if (!categoryRepository.existsById(category.getId())) {
                categoryRepository.save(category);
            }
        }
    }
}
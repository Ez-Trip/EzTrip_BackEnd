package com.eztrip.initializer;  // 패키지 선언

import com.eztrip.entity.category.Category;
import com.eztrip.repository.category.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryInitializer {

    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        List<Category> categories = Arrays.asList(
                new Category("A1", "Category A1"),
                new Category("A2", "Category A2"),
                new Category("A3", "Category A3"),
                new Category("A4", "Category A4"),
                new Category("B1", "Category B1"),
                new Category("B2", "Category B2"),
                new Category("B3", "Category B3"),
                new Category("C1", "Category C1"),
                new Category("D1", "Category D1"),
                new Category("E1", "Category E1")
        );

        for (Category category : categories) {
            if (!categoryRepository.existsById(category.getId())) {
                categoryRepository.save(category);
            }
        }
    }
}
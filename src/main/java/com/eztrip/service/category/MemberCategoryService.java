package com.eztrip.service.category;

import com.eztrip.entity.category.Category;
import com.eztrip.entity.category.MemberCategory;
import com.eztrip.entity.member.Member;
import com.eztrip.repository.category.MemberCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCategoryService {

    private final MemberCategoryRepository memberCategoryRepository;

    public void createMemberCategory(Member member, Category category, Integer preference){

        MemberCategory createMemberCategory = MemberCategory.builder()
                .member(member)
                .category(category)
                .preference(preference)
                .build();

        memberCategoryRepository.save(createMemberCategory);
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        memberCategoryRepository.deleteByMemberId(memberId);  // DB에서 기존 선호도 삭제
    }
}

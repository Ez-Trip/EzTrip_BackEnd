package com.eztrip.service.sns;

import com.eztrip.entity.member.Member;
import com.eztrip.entity.sns.SnsPost;
import com.eztrip.repository.member.MemberRepository;
import com.eztrip.repository.sns.SnsPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SnsPostService {

    private final SnsPostRepository snsPostRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public SnsPost createPost(Long memberId, String image, String dateCourse) {
        validateDateCourseFormat(dateCourse); // 데이트 코스 형식 검증

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 ID: " + memberId));

        SnsPost post = SnsPost.builder()
                .member(member)
                .image(image)
                .dateCourse(dateCourse)
                .build();

        return snsPostRepository.save(post);
    }

    // 모든 포스트 조회
    public List<SnsPost> getAllPosts() {
        return snsPostRepository.findAll();
    }

    // 특정 사용자의 포스트 조회
    public List<SnsPost> getPostsByMemberId(Long memberId) {
        return snsPostRepository.findByMemberId(memberId);
    }

    // 포스트 삭제
    @Transactional
    public void deletePost(Long postId) {
        snsPostRepository.deleteById(postId);
    }

    // 데이트 코스 형식 검증 메서드
    private void validateDateCourseFormat(String dateCourse) {
        if (!dateCourse.matches("(A[1-4])?(B[1-3])?(C1)?(D1)?(E1)?")) {
            throw new IllegalArgumentException("잘못된 데이트 코스 형식입니다: " + dateCourse);
        }
    }
}
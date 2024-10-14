package com.eztrip.service.sns;

import com.eztrip.dto.sns.SnsPostRequest;
import com.eztrip.entity.member.Member;
import com.eztrip.entity.sns.SnsPost;
import com.eztrip.repository.member.MemberRepository;
import com.eztrip.repository.SnsPostRepository;
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
    public SnsPost createPost(SnsPostRequest request) {
        validateDateCourseFormat(request.getDateCourse());

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 ID: " + request.getMemberId()));

        SnsPost post = SnsPost.builder()
                .member(member)
                .image(request.getImage())
                .dateCourse(request.getDateCourse())
                .build();

        return snsPostRepository.save(post);
    }

    public List<SnsPost> getAllPosts() {
        return snsPostRepository.findAll();
    }

    public List<SnsPost> getPostsByMemberId(Long memberId) {
        return snsPostRepository.findByMemberId(memberId);
    }

    @Transactional
    public void deletePost(Long postId) {
        snsPostRepository.deleteById(postId);
    }

    private void validateDateCourseFormat(String dateCourse) {
        if (!dateCourse.matches("(A[1-4])?(B[1-3])?(C1)?(D1)?(E1)?")) {
            throw new IllegalArgumentException("잘못된 데이트 코스 형식입니다: " + dateCourse);
        }
    }
}
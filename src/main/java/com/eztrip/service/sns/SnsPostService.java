package com.eztrip.service.sns;

import com.eztrip.dto.sns.SnsPostRequest;
import com.eztrip.entity.member.Member;
import com.eztrip.entity.schedule.Schedule;
import com.eztrip.entity.sns.SnsPost;
import com.eztrip.repository.member.MemberRepository;
import com.eztrip.repository.sns.SnsPostRepository;
import com.eztrip.repository.schedule.ScheduleRepository;
import com.eztrip.repository.schedule.PathDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SnsPostService {

    private final SnsPostRepository snsPostRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public SnsPost createPost(SnsPostRequest request) {
        validateDateCourseFormat(request.getDateCourse());

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 ID: " + request.getMemberId()));

        // Schedule 정보 조회
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스케줄 ID: " + request.getScheduleId()));

        // SnsPost 객체 생성
        SnsPost post = SnsPost.builder()
                .member(member)
                .image(request.getImage())
                .dateCourse(request.getDateCourse())
                .schedule(schedule)  // Schedule을 정확히 할당
                .build();

        // SnsPost 저장
        return snsPostRepository.save(post);
    }

    // 모든 포스트 조회
    public List<SnsPost> getAllPosts() {
        return snsPostRepository.findAll();
    }

    // 특정 사용자의 포스트 조회
    public List<SnsPost> getPostsByMemberId(Long memberId) {
        return snsPostRepository.findByMemberId(memberId);  // Member ID에 따른 포스트 조회
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
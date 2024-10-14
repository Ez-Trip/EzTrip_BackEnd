package com.eztrip.repository.sns;

import com.eztrip.entity.sns.SnsPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnsPostRepository extends JpaRepository<SnsPost, Long> {
    List<SnsPost> findByMemberId(Long memberId); // 특정 사용자가 공유한 내용 조회
}
package com.eztrip.controller.sns;

import com.eztrip.entity.sns.SnsPost;
import com.eztrip.service.sns.SnsPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sns/posts")
@RequiredArgsConstructor
public class SnsPostController {

    private final SnsPostService snsPostService;

    // SNS 포스트 생성
    @PostMapping
    public SnsPost createPost(@RequestBody SnsPostRequest snsPostRequest) {
        return snsPostService.createPost(snsPostRequest.getMemberId(), snsPostRequest.getImage(), snsPostRequest.getDateCourse());
    }

    // 모든 포스트 조회
    @GetMapping
    public List<SnsPost> getAllPosts() {
        return snsPostService.getAllPosts();
    }

    // 특정 사용자의 포스트 조회
    @GetMapping("/member/{memberId}")
    public List<SnsPost> getPostsByMemberId(@PathVariable Long memberId) {
        return snsPostService.getPostsByMemberId(memberId);
    }

    // 포스트 삭제
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        snsPostService.deletePost(postId);
    }
}
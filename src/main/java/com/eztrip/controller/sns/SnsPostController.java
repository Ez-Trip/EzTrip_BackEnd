package com.eztrip.controller.sns;

import com.eztrip.dto.sns.SnsPostDto;
import com.eztrip.dto.sns.SnsPostRequest;
import com.eztrip.entity.sns.SnsPost;
import com.eztrip.service.sns.SnsPostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sns/posts")
@RequiredArgsConstructor
public class SnsPostController {

    private final SnsPostService snsPostService;

    // SNS 포스트 생성
    @PostMapping
    @Operation(summary = "snspost 생성")
    public SnsPostDto createPost(@RequestBody SnsPostRequest snsPostRequest) {
        SnsPost post = snsPostService.createPost(snsPostRequest);
        return new SnsPostDto(post);
    }

    // 모든 포스트 조회
    @GetMapping
    @Operation(summary = "모든 snspost 조회")
    public List<SnsPostDto> getAllPosts() {
        return snsPostService.getAllPosts()
                .stream()
                .map(SnsPostDto::new)
                .collect(Collectors.toList());
    }

    // 특정 사용자의 포스트 조회
    @GetMapping("/member/{memberId}")
    @Operation(summary = "Member 고유 id 값으로 snspost조회")
    public List<SnsPostDto> getPostsByMemberId(@PathVariable Long memberId) {
        return snsPostService.getPostsByMemberId(memberId)
                .stream()
                .map(SnsPostDto::new)
                .collect(Collectors.toList());
    }

    // 포스트 삭제
    @DeleteMapping("/{postId}")
    @Operation(summary = "snspost 고유 id 로 삭제")
    public void deletePost(@PathVariable Long postId) {
        snsPostService.deletePost(postId);
    }
}
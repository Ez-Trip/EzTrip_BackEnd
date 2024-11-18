package com.eztrip.controller.sns;

import com.eztrip.dto.sns.SnsPostDto;
import com.eztrip.dto.sns.SnsPostRequest;
import com.eztrip.entity.sns.SnsPost;
import com.eztrip.service.sns.SnsPostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sns/posts")
@RequiredArgsConstructor
public class SnsPostController {

    private final SnsPostService snsPostService;

    @PostMapping
    @Operation(summary = "SnsPost 생성")
    public ResponseEntity<SnsPostDto> createPost(@RequestBody SnsPostRequest snsPostRequest) {
        SnsPost post = snsPostService.createPost(snsPostRequest);

        // SnsPostDto로 변환하여 반환
        SnsPostDto snsPostDto = new SnsPostDto(post);

        return new ResponseEntity<>(snsPostDto, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "모든 snspost 조회")
    public List<SnsPostDto> getAllPosts() {
        return snsPostService.getAllPosts()
                .stream()
                .map(SnsPostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/member/{memberId}")
    @Operation(summary = "Member 고유 id 값으로 snspost조회")
    public List<SnsPostDto> getPostsByMemberId(@PathVariable Long memberId) {
        return snsPostService.getPostsByMemberId(memberId)
                .stream()
                .map(SnsPostDto::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "snspost 고유 id로 삭제")
    public void deletePost(@PathVariable Long postId) {
        snsPostService.deletePost(postId);
    }
}
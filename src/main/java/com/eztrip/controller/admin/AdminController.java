package com.eztrip.controller.admin;

import com.eztrip.dto.member.MemberResponseDto;
import com.eztrip.entity.member.Member;
import com.eztrip.entity.schedule.Schedule;
import com.eztrip.entity.sns.SnsPost;
import com.eztrip.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    // 모든 사용자 조회
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<Member>> getAllUsers() {
        log.info("get user List");
        List<Member> users = adminService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // 특정 사용자 조회 (선호도 정보 포함)
    @GetMapping("/users/{id}")
    public ResponseEntity<MemberResponseDto> getUserById(@PathVariable Long id) {
        MemberResponseDto memberResponse = adminService.getUserById(id);
        return ResponseEntity.ok(memberResponse);
    }

    // 사용자 삭제
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 사용자 역할 변경
    @PatchMapping("/users/{id}/role")
    public ResponseEntity<Member> changeUserRole(@PathVariable Long id, @RequestBody String role) {
        return ResponseEntity.ok(adminService.changeUserRole(id, role));
    }

    // 모든 SNS 조회
    @GetMapping("/snspost")
    public ResponseEntity<List<SnsPost>> getAllSnspost() {
        return ResponseEntity.ok(adminService.getAllSnspost());
    }

    // SNS 삭제
    @DeleteMapping("/snspost/{id}")
    public ResponseEntity<Void> deleteSnspost(@PathVariable Long id) {
        adminService.deleteSnspost(id);
        return ResponseEntity.noContent().build();
    }

    // 모든 스케줄 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(adminService.getAllSchedules());
    }

    // 스케줄 삭제 (SNS와 연동)
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        adminService.deleteScheduleWithSNS(id);
        return ResponseEntity.noContent().build();
    }
}
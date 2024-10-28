package com.eztrip.controller.admin;

import com.eztrip.entity.member.Member;
import com.eztrip.entity.schedule.Schedule;
import com.eztrip.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 모든 사용자 조회
    @GetMapping("/users")
    public ResponseEntity<List<Member>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // 특정 사용자 조회
    @GetMapping("/users/{id}")
    public ResponseEntity<Member> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
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
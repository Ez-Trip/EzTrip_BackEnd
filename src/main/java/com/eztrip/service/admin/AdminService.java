package com.eztrip.service.admin;

import com.eztrip.dto.member.MemberResponseDto;
import com.eztrip.entity.category.MemberCategory;
import com.eztrip.entity.member.Member;
import com.eztrip.entity.member.Role;
import com.eztrip.entity.schedule.Schedule;
import com.eztrip.entity.sns.SnsPost;
import com.eztrip.repository.member.MemberRepository;
import com.eztrip.repository.schedule.ScheduleRepository;
import com.eztrip.repository.sns.SnsPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final SnsPostRepository snsPostRepository;

    // 모든 사용자 조회
    public List<Member> getAllUsers() {
        return memberRepository.findAll();
    }

    // 특정 사용자 조회 - 선호도 포함
    public MemberResponseDto getUserById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. ID: " + id));
        return new MemberResponseDto(member);
    }

    // 사용자 삭제
    public void deleteUser(Long id) {
        memberRepository.deleteById(id);
    }

    // 사용자 역할 변경
    public Member changeUserRole(Long id, String role) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. ID: " + id));
        member.setRole(Role.from(role));  // 역할 변경
        return memberRepository.save(member);
    }

    // 모든 SNS 조회
    public List<SnsPost> getAllSnspost() {
        return snsPostRepository.findAll();
    }

    // SNS 삭제
    @Transactional
    public void deleteSnspost(Long id) {
        // SNS 게시물을 ID로 삭제
        snsPostRepository.deleteById(id);
    }

    // 모든 스케줄 조회
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // 스케줄 삭제와 관련된 SNS 게시물 삭제 처리
    public void deleteScheduleWithSNS(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("스케줄을 찾을 수 없습니다. ID: " + id));

        // 해당 스케줄과 연관된 SNS 게시물 삭제
        snsPostRepository.deleteBySchedule(schedule);

        // 스케줄 삭제
        scheduleRepository.delete(schedule);
    }
}
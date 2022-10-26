package com.jhs.crud.issue.service;

import com.jhs.crud.auth.config.UserDetailsImpl;
import com.jhs.crud.auth.domain.User;
import com.jhs.crud.issue.domain.Issue;
import com.jhs.crud.issue.dto.IssueResponseDto;
import com.jhs.crud.issue.dto.IssueRequestDto;
import com.jhs.crud.issue.repository.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

    @Transactional
    public ResponseEntity<?> setIssue(IssueRequestDto issueRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        issueRepository.save(Issue.builder()
                .title(issueRequestDto.getTitle())
                .content(issueRequestDto.getContent())
                .reg_dt(LocalDateTime.now())
                .user(user)
                .build());

        return ResponseEntity.ok("등록이 완료 되었 습니다.");
    }

    public ResponseEntity<?> getAllIssue() {
        List<IssueResponseDto> responseDtos = new ArrayList<>();

        for (Issue issue : issueRepository.findAll()){
            IssueResponseDto issueResponseDto = new IssueResponseDto(issue);
            responseDtos.add(issueResponseDto);
        }
        return ResponseEntity.ok(responseDtos);
    }

    public ResponseEntity<?> getIssue(Long issue_idx) {
        Issue issue = issueRepository.findById(issue_idx).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다.")
        );
        IssueResponseDto issueResponseDto = new IssueResponseDto(issue);

        return ResponseEntity.ok(issueResponseDto);
    }

    @Transactional
    public ResponseEntity<?> modifyIssue(Long issue_idx, IssueRequestDto issueRequestDto) {
        Issue issue = issueRepository.findById(issue_idx).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다.")
        );
        issue.update(issueRequestDto);
        return ResponseEntity.ok("수정이 완료 되었습니다.");
    }

    public ResponseEntity<?> deleteIssue(Long issue_idx) {
        Issue issue = issueRepository.findById(issue_idx).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다")
        );
        issueRepository.delete(issue);
        return ResponseEntity.ok("삭제가 완료 되었습니다.");
    }
}

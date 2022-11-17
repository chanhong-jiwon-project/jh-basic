package com.jhs.crud.issue.service;

import com.jhs.crud.auth.config.UserDetailsImpl;
import com.jhs.crud.auth.repository.UserRepository;
import com.jhs.crud.enm.Importance;
import com.jhs.crud.enm.Item;
import com.jhs.crud.enm.State;
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
    private final UserRepository userRepository;

    /**
     * 이슈 등록
     * */
    @Transactional
    public ResponseEntity<?> setIssue(IssueRequestDto issueRequestDto, UserDetailsImpl userDetails) {
        String relation_mem = String.join(",", issueRequestDto.getRelation_mem());
        List<String> relationList = issueRequestDto.getRelation_mem();

        //관련자 확인
        for (String r_mem : relationList) {
            userRepository.findByEmail(r_mem).orElseThrow(
                    () -> new IllegalArgumentException("등록되지 않은 관련(회원) 입니다")
            );
        }
        //담당자 확인
        userRepository.findByEmail(issueRequestDto.getManagement_mem()).orElseThrow(
                () -> new IllegalArgumentException("등록되지 않은 담당자(회원) 입니다"));
        //항목 확인
        if (Item.nameOf(issueRequestDto.getItem().getName()) == null)
            throw new IllegalArgumentException("항목이 일치하지 않습니다");
        //상태확인
        if (State.nameOf(issueRequestDto.getState().getName()) == null)
            throw new IllegalArgumentException("상태가 일치하지 않습니다");
        //중요도 확인
        if (Importance.nameOf(issueRequestDto.getImportance().getName()) == null)
            throw new IllegalArgumentException("중요도가 일치하지 않습니다");

        issueRepository.save(Issue.builder()
                .title(issueRequestDto.getTitle())
                .content(issueRequestDto.getContent())
                .reg_dt(LocalDateTime.now())
                .email(userDetails.getUsername())
                .relation_mem(relation_mem)
                .item(issueRequestDto.getItem())
                .state(issueRequestDto.getState())
                .importance(issueRequestDto.getImportance())
                .manegemanet_mem(issueRequestDto.getManagement_mem())
                .build());

        return ResponseEntity.ok("등록이 완료 되었 습니다.");
    }

    /**
     * 이슈 목록 조회
     * */
    public ResponseEntity<?> getAllIssue() {
        List<IssueResponseDto> responseDtos = new ArrayList<>();


        for (Issue issue : issueRepository.findAll()){
            IssueResponseDto issueResponseDto = new IssueResponseDto(issue);
            responseDtos.add(issueResponseDto);
        }

        return ResponseEntity.ok(responseDtos);
    }

    /**
     * 이슈 상세조회
     * */
    public ResponseEntity<?> getIssue(Long issue_idx) {
        Issue issue = issueRepository.findById(issue_idx).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다.")
        );
        IssueResponseDto issueResponseDto = new IssueResponseDto(issue);

        return ResponseEntity.ok(issueResponseDto);
    }

    /**
     * 이슈 수정
     * */
    @Transactional
    public ResponseEntity<?> modifyIssue(Long issue_idx, IssueRequestDto issueRequestDto) {
        Issue issue = issueRepository.findById(issue_idx).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다.")
        );

        if (issueRequestDto.getTitle() == null) {
            issueRequestDto.setTitle(issue.getTitle());
        }
        if (issueRequestDto.getContent() == null) {
            issueRequestDto.setContent(issue.getContent());
        }
        if (issueRequestDto.getRelation_mem() == null) {
            issueRequestDto.setRelation_mem(List.of(issue.getRelation_mem().split(",")));
        }
        if (issueRequestDto.getManagement_mem() == null) {
            issueRequestDto.setManagement_mem(issue.getManegemanet_mem());
        }
        if (issueRequestDto.getItem() == null) {
            issueRequestDto.setItem(issue.getItem());
        }
        if (issueRequestDto.getState() == null) {
            issueRequestDto.setState(issue.getState());
        }
        if (issueRequestDto.getImportance() == null) {
            issueRequestDto.setImportance(issue.getImportance());
        }
        issue.update(issueRequestDto);
        return ResponseEntity.ok("수정이 완료 되었습니다.");
    }

    /**
     * 이슈 삭제
     * */
    public ResponseEntity<?> deleteIssue(Long issue_idx) {
        Issue issue = issueRepository.findById(issue_idx).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다"));

        issueRepository.delete(issue);
        return ResponseEntity.ok("삭제가 완료 되었습니다.");
    }
}

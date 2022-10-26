package com.jhs.crud.issue.controller;

import com.jhs.crud.auth.config.UserDetailsImpl;
import com.jhs.crud.issue.dto.IssueRequestDto;
import com.jhs.crud.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping(value = "/issue")
    public ResponseEntity<?> setIssue(@RequestBody IssueRequestDto issueRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return issueService.setIssue(issueRequestDto, userDetails);
    }

    @GetMapping(value = "/issue/list")
    public ResponseEntity<?> getAllIssue() {
        return issueService.getAllIssue();
    }

    @GetMapping(value = "/issue/{issue_idx}")
    public ResponseEntity<?> getIssue(@PathVariable Long issue_idx) {
        return issueService.getIssue(issue_idx);
    }

    @PutMapping(value = "/issue/{issue_idx}")
    public ResponseEntity<?> modifyIssue(@PathVariable Long issue_idx, @RequestBody IssueRequestDto issueRequestDto) {
        return issueService.modifyIssue(issue_idx, issueRequestDto);
    }

    @DeleteMapping(value = "issue/{issue_idx}")
    public ResponseEntity<?> deleteIssue(@PathVariable Long issue_idx) {
        return issueService.deleteIssue(issue_idx);
    }
}

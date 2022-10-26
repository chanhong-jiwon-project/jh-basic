package com.jhs.crud.issue.dto;

import com.jhs.crud.issue.domain.Issue;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class IssueResponseDto {
    private Long idx;
    private String title;
    private String content;
    private String email;
    private LocalDateTime reg_dt;

    public IssueResponseDto(Issue issue) {
        this.idx = issue.getIdx();
        this.title = issue.getTitle();
        this.content = issue.getContent();
        this.email = issue.getUser().getEmail();
        this.reg_dt = issue.getReg_dt();
    }
}

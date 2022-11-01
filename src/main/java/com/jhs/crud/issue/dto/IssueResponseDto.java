package com.jhs.crud.issue.dto;

import com.jhs.crud.issue.domain.Issue;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class IssueResponseDto {
    private Long idx;
    private String title;
    private String content;
    private String email;
    private List<String> relation_mem;
    private LocalDateTime reg_dt;

    public IssueResponseDto(Issue issue) {
        this.idx = issue.getIdx();
        this.title = issue.getTitle();
        this.content = issue.getContent();
        this.email = issue.getEmail();
        this.reg_dt = issue.getReg_dt();
        this.relation_mem = List.of(issue.getRelation_mem().split(","));

    }
}

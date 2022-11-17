package com.jhs.crud.issue.dto;

import com.jhs.crud.enm.Importance;
import com.jhs.crud.enm.Item;
import com.jhs.crud.enm.State;
import com.jhs.crud.issue.domain.Issue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class IssueResponseDto {
    private Long idx;
    private String title;
    private String content;
    private String email;
    private List<String> relation_mem;
    private String management_mem;
    private Item item;
    private Importance importance;
    private State state;
    private LocalDateTime reg_dt;

    public IssueResponseDto(Issue issue) {
        this.idx = issue.getIdx();
        this.title = issue.getTitle();
        this.content = issue.getContent();
        this.email = issue.getEmail();
        this.reg_dt = issue.getReg_dt();
        this.relation_mem = List.of(issue.getRelation_mem().split(","));
        this.management_mem = issue.getManegemanet_mem();
        this.item = issue.getItem();
        this.importance = issue.getImportance();
        this.state = issue.getState();
    }
}

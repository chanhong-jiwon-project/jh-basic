package com.jhs.crud.issue.domain;

import com.jhs.crud.enm.Importance;
import com.jhs.crud.enm.Item;
import com.jhs.crud.enm.State;
import com.jhs.crud.issue.dto.IssueRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "issue")
@Getter
@Builder
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String relation_mem;

    @Column(nullable = true)
    private String  manegemanet_mem;

    @Column(nullable = false)
    private LocalDateTime reg_dt;

    @Column(nullable = true)
    private LocalDateTime upd_dt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Item item;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Importance importance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private State state;

    public void update(IssueRequestDto issueRequestDto) {
        this.title = issueRequestDto.getTitle();
        this.content = issueRequestDto.getContent();
        this.relation_mem = String.join(",", issueRequestDto.getRelation_mem());
        this.manegemanet_mem = issueRequestDto.getManagement_mem();
        this.item = issueRequestDto.getItem();
        this.importance = issueRequestDto.getImportance();
        this.state = issueRequestDto.getState();
        this.upd_dt = LocalDateTime.now();
    }

}

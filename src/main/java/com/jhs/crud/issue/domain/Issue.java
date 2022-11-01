package com.jhs.crud.issue.domain;

import com.jhs.crud.auth.domain.User;
import com.jhs.crud.issue.dto.IssueRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    public void update(IssueRequestDto issueRequestDto) {
        this.title = issueRequestDto.getTitle();
        this.content = issueRequestDto.getContent();
        this.relation_mem = String.join(",", issueRequestDto.getRelation_mem());
        this.upd_dt = LocalDateTime.now();
    }

}

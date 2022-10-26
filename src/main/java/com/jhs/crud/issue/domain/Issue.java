package com.jhs.crud.issue.domain;

import com.jhs.crud.auth.domain.User;
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

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime reg_dt;

    @Column(nullable = true)
    private LocalDateTime upd_dt;

    public void update(IssueRequestDto issueRequestDto) {
        this.title = issueRequestDto.getTitle();
        this.content = issueRequestDto.getContent();
        this.upd_dt = LocalDateTime.now();
    }

}

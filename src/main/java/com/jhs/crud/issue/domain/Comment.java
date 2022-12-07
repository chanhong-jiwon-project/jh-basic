package com.jhs.crud.issue.domain;

import com.jhs.crud.issue.dto.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
@Getter
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Long issueIdx;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime reg_dt;

    @Column(nullable = true)
    private LocalDateTime upd_dt;

    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
        this.upd_dt = LocalDateTime.now();
    }
}

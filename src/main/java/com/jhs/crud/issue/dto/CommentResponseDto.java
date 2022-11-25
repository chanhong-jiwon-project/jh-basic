package com.jhs.crud.issue.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long comment_idx;
    private String comment;
    private LocalDateTime reg_dt;
    private String email;

    public CommentResponseDto(Long comment_idx, String comment, LocalDateTime reg_dt, String email) {
        this.comment_idx = comment_idx;
        this.comment = comment;
        this.reg_dt = reg_dt;
        this.email = email;
    }
}

package com.jhs.crud.issue.service;

import com.jhs.crud.auth.config.UserDetailsImpl;
import com.jhs.crud.issue.domain.Comment;
import com.jhs.crud.issue.dto.CommentRequestDto;
import com.jhs.crud.issue.dto.CommentResponseDto;
import com.jhs.crud.issue.repository.CommentRepository;
import com.jhs.crud.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;

    /**
     * 댓글달기
     * */
    @Transactional
    public ResponseEntity<?> setComment(CommentRequestDto commentRequestDto, Long issue_idx, UserDetailsImpl userDetails) {

        issueRepository.findById(issue_idx).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다.")
        );

        commentRepository.save(Comment.builder()
                .comment(commentRequestDto.getComment())
                .email(userDetails.getUsername())
                .reg_dt(LocalDateTime.now())
                .issueIdx(issue_idx)
                .build());

        return ResponseEntity.ok("댓글이 입력 되었습니다.");
    }

    /**
     * 댓글 가져오기
     * */
    public ResponseEntity<?> getComment(Long issue_idx) {
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        issueRepository.findById(issue_idx).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다.")
        );

        List<Comment> commentList = commentRepository.findByIssueIdx(issue_idx);
        for (Comment comment : commentList) {
            commentResponseDtoList.add(CommentResponseDto.builder()
                    .comment_idx(comment.getIdx())
                    .email(comment.getEmail())
                    .comment(comment.getComment())
                    .reg_dt(LocalDateTime.now())
                    .build());
        }

        return ResponseEntity.ok(commentResponseDtoList);
    }

    /**
     * 댓글 수정
     * */
    @Transactional
    public ResponseEntity<?> putComment(Long comment_idx, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {

        Comment comment = commentRepository.findById(comment_idx).orElseThrow(
                () -> new NullPointerException("댓글이 존재하지 않습니다.")
        );

        System.out.println(userDetails.getUser().getEmail());

        if (!userDetails.getUser().getEmail().equals(comment.getEmail())) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");
        }

        if (commentRequestDto.getComment() == null) {
            throw new IllegalArgumentException("내용을 입력해 주세요");
        }

        comment.update(commentRequestDto);
        return ResponseEntity.ok("수정이 완료 되었습니다.");
    }

    /**
     * 댓글 삭제
     * */
    public ResponseEntity<?> deleteComment(Long commnet_idx) {
        Comment comment = commentRepository.findById(commnet_idx).orElseThrow(
                () -> new NullPointerException("댓글이 존재하지 않습니다.")
        );
        commentRepository.delete(comment);

        return ResponseEntity.ok("삭제 완료 되었습니다.");
    }
}
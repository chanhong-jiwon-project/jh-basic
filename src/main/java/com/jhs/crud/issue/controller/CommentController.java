package com.jhs.crud.issue.controller;

import com.jhs.crud.auth.config.UserDetailsImpl;
import com.jhs.crud.issue.dto.CommentRequestDto;
import com.jhs.crud.issue.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/issue/{issue_idx}/comment")
    public ResponseEntity<?> setComment(@RequestBody CommentRequestDto commentRequestDto,
                                        @PathVariable Long issue_idx,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.setComment(commentRequestDto, issue_idx, userDetails);
    }

    @GetMapping(value = "/issue/{issue_idx}/comments")
    public ResponseEntity<?> getComment(@PathVariable Long issue_idx) {
        return commentService.getComment(issue_idx);
    }

    @PutMapping(value = "/issue/{issue_idx}/{comment_idx}/comment")
    public ResponseEntity<?> putComment(@PathVariable Long comment_idx,
                                        @RequestBody CommentRequestDto commentRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.putComment(comment_idx, commentRequestDto, userDetails);
    }

    @DeleteMapping(value = "/issue/{issue_idx}/{comment_idx}/comment")
    public ResponseEntity<?> deleteComment(@PathVariable Long commnet_idx) {
        return commentService.deleteComment(commnet_idx);
    }
}

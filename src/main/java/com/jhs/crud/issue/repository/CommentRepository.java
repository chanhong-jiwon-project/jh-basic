package com.jhs.crud.issue.repository;

import com.jhs.crud.issue.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByIssueIdx(Long issue_idx);
}

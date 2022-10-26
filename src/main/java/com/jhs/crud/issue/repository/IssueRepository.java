package com.jhs.crud.issue.repository;

import com.jhs.crud.issue.domain.Issue;
import com.jhs.crud.issue.dto.IssueRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {
}

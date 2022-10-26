package com.jhs.crud.issue.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueRequestDto {
    private String title;
    private String content;
}

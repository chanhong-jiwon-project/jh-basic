package com.jhs.crud.issue.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IssueRequestDto {
    private String title;
    private String content;
    private List<String> relation_mem;
    private String management_mem;
}

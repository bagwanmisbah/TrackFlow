package com.misbah.issue_tracker.dto;

import com.misbah.issue_tracker.model.Priority;
import lombok.Data;

import java.util.Set;

@Data
public class IssueRequest {
    private String title;
    private String description;
    private Long assigneeId; // The ID of the user to assign the issue to
    private Priority priority;
    private Set<String> tags;
}
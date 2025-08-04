package com.misbah.issue_tracker.dto;

import lombok.Data;

@Data
public class ProjectRequest {
    private String name;
    private String description;
}
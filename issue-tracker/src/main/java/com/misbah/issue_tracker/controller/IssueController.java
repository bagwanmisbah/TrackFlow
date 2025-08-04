package com.misbah.issue_tracker.controller;

import com.misbah.issue_tracker.dto.IssueRequest;
import com.misbah.issue_tracker.model.Issue;
import com.misbah.issue_tracker.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping("/projects/{projectId}/issues")
    public ResponseEntity<Issue> createIssue(
            @PathVariable Long projectId,
            @RequestBody IssueRequest issueRequest) {

        Issue newIssue = issueService.createIssue(projectId, issueRequest);
        return ResponseEntity.ok(newIssue);
    }
    @GetMapping("/issues/my-issues")
    public ResponseEntity<List<Issue>> getMyAssignedIssues() {
        return ResponseEntity.ok(issueService.getIssuesAssignedToCurrentUser());
    }
    @PutMapping("/issues/{issueId}")
    public ResponseEntity<Issue> updateIssue(
            @PathVariable Long issueId,
            @RequestBody IssueRequest issueRequest) {

        Issue updatedIssue = issueService.updateIssue(issueId, issueRequest);
        return ResponseEntity.ok(updatedIssue);
    }
    @GetMapping("/issues/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) {
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }
}
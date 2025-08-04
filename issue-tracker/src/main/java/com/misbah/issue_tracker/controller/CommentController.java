package com.misbah.issue_tracker.controller;

import com.misbah.issue_tracker.dto.CommentRequest;
import com.misbah.issue_tracker.model.Comment;
import com.misbah.issue_tracker.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/issues/{issueId}/comments")
    public ResponseEntity<Comment> createComment(
            @PathVariable Long issueId,
            @RequestBody CommentRequest commentRequest) {

        Comment newComment = commentService.createComment(issueId, commentRequest);
        return ResponseEntity.ok(newComment);
    }
}
package com.misbah.issue_tracker.service;

import com.misbah.issue_tracker.dto.CommentRequest;
import com.misbah.issue_tracker.exception.ResourceNotFoundException;
import com.misbah.issue_tracker.model.Comment;
import com.misbah.issue_tracker.model.Issue;
import com.misbah.issue_tracker.model.User;
import com.misbah.issue_tracker.repository.CommentRepository;
import com.misbah.issue_tracker.repository.IssueRepository;
import com.misbah.issue_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public Comment createComment(Long issueId, CommentRequest commentRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + issueId));

        boolean isUserAssigned = issue.getProject().getAssignedUsers().stream()
                .anyMatch(user -> user.getId().equals(currentUser.getId()));

        if (!isUserAssigned) {
            throw new AccessDeniedException("User is not a member of the project this issue belongs to");
        }

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setIssue(issue);
        comment.setAuthor(currentUser);

        return commentRepository.save(comment);
    }
}
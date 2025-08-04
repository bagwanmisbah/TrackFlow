package com.misbah.issue_tracker.service;

import com.misbah.issue_tracker.dto.IssueRequest;
import com.misbah.issue_tracker.exception.ResourceNotFoundException;
import com.misbah.issue_tracker.model.Issue;
import com.misbah.issue_tracker.model.Project;
import com.misbah.issue_tracker.model.Role;
import com.misbah.issue_tracker.model.User;
import com.misbah.issue_tracker.repository.IssueRepository;
import com.misbah.issue_tracker.repository.ProjectRepository;
import com.misbah.issue_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Issue createIssue(Long projectId, IssueRequest issueRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        boolean isUserAssigned = project.getAssignedUsers().stream()
                .anyMatch(user -> user.getId().equals(currentUser.getId()));

        if (!isUserAssigned) {
            throw new AccessDeniedException("User is not assigned to this project");
        }

        User assignee = null;
        if (issueRequest.getAssigneeId() != null) {
            assignee = userRepository.findById(issueRequest.getAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assignee user not found with id: " + issueRequest.getAssigneeId()));
        }

        Issue issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus("TO_DO");
        issue.setProject(project);
        issue.setAssignee(assignee);
        issue.setPriority(issueRequest.getPriority());
        issue.setTags(issueRequest.getTags());

        return issueRepository.save(issue);
    }
    public Issue updateIssue(Long issueId, IssueRequest issueRequest) {
        Issue issueToUpdate = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + issueId));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        boolean isUserAssigned = issueToUpdate.getProject().getAssignedUsers().stream()
                .anyMatch(user -> user.getId().equals(currentUser.getId()));
        if (!isUserAssigned) {
            throw new AccessDeniedException("User is not a member of this issue's project");
        }

        if (issueRequest.getTitle() != null) {
            issueToUpdate.setTitle(issueRequest.getTitle());
        }
        if (issueRequest.getDescription() != null) {
            issueToUpdate.setDescription(issueRequest.getDescription());
        }
        if (issueRequest.getAssigneeId() != null) {
            User assignee = userRepository.findById(issueRequest.getAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assignee user not found with id: " + issueRequest.getAssigneeId()));
            issueToUpdate.setAssignee(assignee);
        }
        if (issueRequest.getPriority() != null) {
            issueToUpdate.setPriority(issueRequest.getPriority());
        }
        if (issueRequest.getTags() != null) {
            issueToUpdate.setTags(issueRequest.getTags());
        }

        return issueRepository.save(issueToUpdate);
    }
    public List<Issue> getIssuesAssignedToCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        return issueRepository.findByAssigneeId(currentUser.getId());
    }
    public Issue getIssueById(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + issueId));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        boolean isUserAssigned = issue.getProject().getAssignedUsers().stream()
                .anyMatch(user -> user.getId().equals(currentUser.getId()));

        if (currentUser.getRole() != Role.ADMIN && !isUserAssigned) {
            throw new AccessDeniedException("You are not authorized to view this issue");
        }

        return issue;
    }
}
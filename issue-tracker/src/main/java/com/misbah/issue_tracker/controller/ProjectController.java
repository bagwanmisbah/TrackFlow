package com.misbah.issue_tracker.controller;

import com.misbah.issue_tracker.dto.ProjectRequest;
import com.misbah.issue_tracker.model.Project;
import com.misbah.issue_tracker.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.misbah.issue_tracker.dto.AssignUserRequest;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Project> createProject(@RequestBody ProjectRequest projectRequest) {
        Project createdProject = projectService.createProject(projectRequest);
        return ResponseEntity.ok(createdProject);
    }
    @PostMapping("/{projectId}/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Project> assignUser(
            @PathVariable Long projectId,
            @RequestBody AssignUserRequest assignUserRequest) {

        Project updatedProject = projectService.assignUserToProject(projectId, assignUserRequest.getUserId());
        return ResponseEntity.ok(updatedProject);
    }
    @GetMapping
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> projects = projectService.getProjectsForCurrentUser();
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) {
        Project project = projectService.getProjectById(projectId);
        return ResponseEntity.ok(project);
    }
}
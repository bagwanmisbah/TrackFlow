package com.misbah.issue_tracker.service;

import com.misbah.issue_tracker.dto.ProjectRequest;
import com.misbah.issue_tracker.model.Project;
import com.misbah.issue_tracker.model.Role;
import com.misbah.issue_tracker.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.misbah.issue_tracker.exception.ResourceNotFoundException;
import com.misbah.issue_tracker.model.User;
import com.misbah.issue_tracker.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Project createProject(ProjectRequest projectRequest) {
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());


        return projectRepository.save(project);
    }
    public Project assignUserToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        project.getAssignedUsers().add(user);

        return projectRepository.save(project);
    }
    public List<Project> getProjectsForCurrentUser()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        if (currentUser.getRole() == Role.ADMIN) {
            return projectRepository.findAll();
        } else {
            return projectRepository.findProjectsByUserId(currentUser.getId());
        }
    }
    public Project getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        boolean isUserAssigned = project.getAssignedUsers().stream()
                .anyMatch(user -> user.getId().equals(currentUser.getId()));

        if (currentUser.getRole() != Role.ADMIN && !isUserAssigned) {
            throw new AccessDeniedException("You are not authorized to view this project");
        }

        return project;
    }
}
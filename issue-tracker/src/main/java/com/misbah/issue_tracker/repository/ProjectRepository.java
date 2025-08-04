package com.misbah.issue_tracker.repository;

import com.misbah.issue_tracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long>
{
    @Query("SELECT p FROM Project p JOIN p.assignedUsers u WHERE u.id = :userId")
    List<Project> findProjectsByUserId(@Param("userId") Long userId);
}

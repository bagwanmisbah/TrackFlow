package com.misbah.issue_tracker.repository;

import com.misbah.issue_tracker.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}

package com.taskflowproject.taskflow.repository;

import com.taskflowproject.taskflow.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

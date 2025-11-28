package com.taskflowproject.taskflow.repository;

import com.taskflowproject.taskflow.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}

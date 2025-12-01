package com.taskflowproject.taskflow.repository;

import com.taskflowproject.taskflow.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
}

package com.taskflowproject.taskflow.repository;

import com.taskflowproject.taskflow.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    boolean existsByUserUserIdAndProjectProjectIdAndRoleRoleId(Long userId, Long projectId, Long roleId);

    List<ProjectMember> findByProjectProjectId(Long projectId);
}

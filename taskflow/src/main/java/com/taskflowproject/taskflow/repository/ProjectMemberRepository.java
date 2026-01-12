package com.taskflowproject.taskflow.repository;

import com.taskflowproject.taskflow.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    boolean existsByUserUserIdAndProjectProjectIdAndRoleRoleId(Long userId, Long projectId, Long roleId);

    List<ProjectMember> findByProjectProjectId(Long projectId);

    List<ProjectMember> findByUserUserId(Long userId);

    Optional<ProjectMember> findByUserUserIdAndProjectProjectId(Long userId, Long projectId);

}

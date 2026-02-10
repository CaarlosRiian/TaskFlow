package com.taskflowproject.taskflow.repository;

import com.taskflowproject.taskflow.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByTeamTeamId(Long teamId);

    boolean existsByTeamTeamIdAndProjectMemberProjectMemberId(Long teamId, Long projectMemberId);
}

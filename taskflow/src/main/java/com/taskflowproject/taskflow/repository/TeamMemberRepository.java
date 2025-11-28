package com.taskflowproject.taskflow.repository;

import com.taskflowproject.taskflow.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}

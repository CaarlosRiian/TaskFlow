package com.taskflowproject.taskflow.repository;

import com.taskflowproject.taskflow.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}

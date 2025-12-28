package com.taskflowproject.taskflow.repository;

import com.taskflowproject.taskflow.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);
}

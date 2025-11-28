package com.taskflowproject.taskflow.repository;

import com.taskflowproject.taskflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}

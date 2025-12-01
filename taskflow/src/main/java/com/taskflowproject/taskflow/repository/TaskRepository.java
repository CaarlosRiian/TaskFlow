package com.taskflowproject.taskflow.repository;

import com.taskflowproject.taskflow.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

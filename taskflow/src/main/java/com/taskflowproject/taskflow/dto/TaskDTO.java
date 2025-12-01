package com.taskflowproject.taskflow.dto;

import java.time.LocalDateTime;

public record TaskDTO (
        Long id,
        String title,
        String description,
        String status,
        String priority,
        LocalDateTime creationDate,
        LocalDateTime dueDate,
        Long assignedTo,
        Long projectId
){}

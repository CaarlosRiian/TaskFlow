package com.taskflowproject.taskflow.dto;

import java.time.LocalDateTime;

public record ProjectDTO (
        Long projectId,
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String status,
        Long managerId
){}

package com.taskflowproject.taskflow.dto;

import java.time.LocalDateTime;

public record ProjectDTO (
        Long id,
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String status,
        Long managerId
){}

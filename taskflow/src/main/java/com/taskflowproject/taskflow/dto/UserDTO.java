package com.taskflowproject.taskflow.dto;

import com.taskflowproject.taskflow.model.enums.UserType;

public record UserDTO (
        Long id,
        String name,
        String email,
        boolean active,
        UserType type
){}
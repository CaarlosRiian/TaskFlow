package com.taskflowproject.taskflow.dto;

import jakarta.validation.constraints.NotBlank;

public record CreationRoleDTO(

        @NotBlank
        String name

) {}

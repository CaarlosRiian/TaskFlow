package com.taskflowproject.taskflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreationTeamDTO(

        @NotBlank
        String name,

        String description,

        @NotNull
        Long leaderId

) {}

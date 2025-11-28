package com.taskflowproject.taskflow.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import com.taskflowproject.taskflow.model.Project;

public record CreationProjectDTO (
        @NotBlank(message = "O nome do projeto é obrigatório!")
        String name,

        String description,

        @NotNull(message = "A data de início é obrigatória")
        LocalDateTime startDate,

        LocalDateTime endDate,

        @NotNull(message = "O ID do gerente é obrigatório!")
        Long managerId,

        @NotNull(message = "O status é obrigatório")
        Project.Status status
) {}

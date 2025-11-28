package com.taskflowproject.taskflow.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record CreationProjectDTO (
    @NotBlank(message = "O nome do projeto é obrigatório!")
    String name,

    String description,

    @NotNull(message = "A data de início é obrigatória")
    LocalDateTime startDate,

    LocalDateTime endDate,

    @NotNull(message = "O ID do gerente é obrigatório!")
    Long managerId

    ){}

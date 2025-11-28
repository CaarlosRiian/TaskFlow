package com.taskflowproject.taskflow.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record CreationTask(
        @NotBlank(message = "O título da tarefa é obrigatório")
        String title,

        String description,

        @NotNull(message = "A tarefa deve possuir uma data limite")
        LocalDateTime dueDate,

        @NotNull(message = "O ID do usuário responsável é obrigatório")
        Long assignedTo,

        @NotNull(message = "O ID do projeto é obrigatório")
        Long projectId
) {
}

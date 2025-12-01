package com.taskflowproject.taskflow.dto;

import jakarta.validation.constraints.*;

public record CreationCommentDTO(
        @NotBlank(message = "O comentário deve possuir uma Mensagem!")
        String message,

        @NotNull(message = "O ID do autor é obrigatório")
        Long authorId,

        @NotNull(message = "O ID da tarefa é obrigatório")
        Long taskId
) {
}

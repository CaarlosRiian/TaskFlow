package com.taskflowproject.taskflow.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentDTO(
        @NotBlank(message = "A mensagem não pode estar vazia")
        String message
) {}

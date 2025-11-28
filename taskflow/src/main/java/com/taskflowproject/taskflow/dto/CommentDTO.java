package com.taskflowproject.taskflow.dto;

import java.time.LocalDateTime;

public record CommentDTO(
        Long id,
        String message,
        LocalDateTime dateTime,
        Long authorId,
        Long taskId
) {
}

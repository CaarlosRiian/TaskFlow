package com.taskflowproject.taskflow.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.taskflowproject.taskflow.model.Project;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidEnum(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException invalidFormatEx) {
            if (invalidFormatEx.getTargetType() == Project.Status.class) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Valor inválido para Status. Valores válidos: CONCLUIDA, EM_REVISAO, DESENVOLVIMENTO, CANCELADA");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON inválido: " + ex.getMessage());
    }
}

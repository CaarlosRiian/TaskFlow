package com.taskflowproject.taskflow.dto;

import com.taskflowproject.taskflow.model.enums.UserType;
import jakarta.validation.constraints.*;

public record CreationUserDTO (
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, message = "O nome deve possuir pelo menos 3 caracteres!")
        String name,

        @Email(message = "O email deve ser válido")
        @NotBlank(message = "O email é obrigatório!")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve possuir pelo menos 6 caracteres!")
        String password,

        UserType type
){}
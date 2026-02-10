package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.LoginRequestDTO;
import com.taskflowproject.taskflow.dto.LoginResponseDTO;
import com.taskflowproject.taskflow.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoint para login e geração de tokens JWT")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Realizar autenticação",
            description = "Recebe as credenciais (e-mail e senha) e retorna um Token JWT válido caso o usuário exista."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
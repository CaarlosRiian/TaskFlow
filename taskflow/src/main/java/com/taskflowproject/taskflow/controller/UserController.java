package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.*;
import com.taskflowproject.taskflow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários do sistema.")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Criar novo usuário", description = "Cadastra um novo usuário no sistema, as senhas são criptografadas antes de salvar.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody @Valid CreationUserDTO dto) {
        return userService.createUser(dto);
    }

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
    @GetMapping
    public List<UserDTO> listUsers() {
        return userService.listUsers();
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os detalhes de um usuário específico.")
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Atualizar usuário", description = "Altera os dados básicos de um usuário existente.")
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody @Valid CreationUserDTO dto) {
        return userService.updateUser(id, dto);
    }

    @Operation(summary = "Excluir usuário", description = "Remove permanentemente um usuário do sistema.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Operation(summary = "Ativar/Desativar usuário", description = "Inverte o status de ativação do usuário | Patch funcional |.")
    @PatchMapping("/{id}/toggle")
    public UserDTO toggleUserActive(@PathVariable Long id) {
        return userService.toggleUserActive(id);
    }
}
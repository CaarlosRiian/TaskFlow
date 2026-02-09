package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.CreationRoleDTO;
import com.taskflowproject.taskflow.dto.RoleDTO;
import com.taskflowproject.taskflow.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/roles")
@Tag(name = "Funções (Roles)", description = "Endpoints para gerenciamento de perfis de acesso (GERENTE, SENIOR, etc.)")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Criar nova função", description = "Cadastra uma nova Role no sistema (ex: ADMIN, DEV).")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO create(@RequestBody @Valid CreationRoleDTO dto) {
        return roleService.create(dto);
    }

    @Operation(summary = "Atualizar função", description = "Altera o nome ou descrição de uma Role existente.")
    @PutMapping("/{id}")
    public RoleDTO updateRole(@PathVariable Long id, @RequestBody @Valid CreationRoleDTO dto) {
        return roleService.updateRole(id, dto);
    }

    @Operation(summary = "Listar todas as funções", description = "Retorna todos os perfis de acesso cadastrados.")
    @GetMapping
    public List<RoleDTO> list() {
        return roleService.listAll();
    }

    @Operation(summary = "Buscar função por ID", description = "Retorna os detalhes de uma Role específica.")
    @GetMapping("/{id}")
    public RoleDTO getRole(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @Operation(summary = "Excluir função", description = "Remove uma Role do sistema. Atenção: isso pode afetar usuários vinculados.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        roleService.delete(id);
    }
}
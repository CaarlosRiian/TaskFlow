package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.*;
import com.taskflowproject.taskflow.service.ProjectService;
import com.taskflowproject.taskflow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projetos", description = "Endpoints para gerenciamento de projetos e membros")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Cria um novo projeto", description = "Apenas administradores podem criar projetos. O criador é definido como gerente.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProjectDTO createProject(@RequestBody @Valid CreationProjectDTO dto) {
        return projectService.createProject(dto);
    }

    @Operation(summary = "Lista projetos do usuário", description = "Retorna os projetos nos quais o usuário autenticado está envolvido.")
    @GetMapping
    public List<ProjectDTO> listProjects(Authentication authentication) {
        Long userId = userService.getUserIdFromAuth(authentication);
        return projectService.listProjectsForUser(userId);
    }

    @Operation(summary = "Busca projeto por ID", description = "Retorna os detalhes de um projeto específico.")
    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @Operation(summary = "Atualiza um projeto", description = "Permite editar os dados do projeto. Restrito a ADMIN ou GERENTE.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GERENTE')")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody @Valid CreationProjectDTO dto) {
        return projectService.updateProject(id, dto);
    }

    @Operation(summary = "Exclui um projeto", description = "Remove o projeto e seus vínculos. Ação irreversível restrita a ADMIN.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @Operation(summary = "Adiciona membro ao projeto", description = "Vincula um usuário ao projeto com a função de Gerente.")
    @PostMapping("/{projectId}/members/{userId}")
    @PreAuthorize("hasAnyAuthority('GERENTE', 'ADMIN')")
    public void addMember(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.addMemberToProject(projectId, userId);
    }
}
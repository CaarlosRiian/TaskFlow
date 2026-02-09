package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.CreationProjectMemberDTO;
import com.taskflowproject.taskflow.dto.ProjectMemberDTO;
import com.taskflowproject.taskflow.dto.UpdateProjectMemberRoleDTO;
import com.taskflowproject.taskflow.service.ProjectMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/project-members")
@Tag(name = "Membros do Projeto", description = "Endpoints para gerenciar quem participa de cada projeto e suas funções")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @Operation(summary = "Adicionar membro ao projeto", description = "Vincula um usuário a um projeto com uma função específica.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectMemberDTO add(@RequestBody @Valid CreationProjectMemberDTO dto) {
        return projectMemberService.add(dto);
    }

    @Operation(summary = "Listar membros por projeto", description = "Retorna todos os usuários que fazem parte de um projeto específico.")
    @GetMapping("/project/{projectId}")
    public List<ProjectMemberDTO> listByProject(@PathVariable Long projectId) {
        return projectMemberService.listByProject(projectId);
    }

    @Operation(summary = "Alterar função do membro", description = "Atualiza o cargo (Role) de um membro dentro do projeto.")
    @PutMapping("/{id}/role")
    public ProjectMemberDTO updateRole(
            @PathVariable Long id,
            @RequestBody UpdateProjectMemberRoleDTO dto
    ) {
        return projectMemberService.updateRole(id, dto.roleId());
    }

    @Operation(summary = "Remover membro do projeto", description = "Retira um usuário do projeto permanentemente.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        projectMemberService.remove(id);
    }
}
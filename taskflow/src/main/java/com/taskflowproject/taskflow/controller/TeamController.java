package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.CreationTeamDTO;
import com.taskflowproject.taskflow.dto.TeamDTO;
import com.taskflowproject.taskflow.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/teams")
@Tag(name = "Equipes", description = "Endpoints para gerenciamento de equipes dentro da organização.")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @Operation(summary = "Criar nova equipe", description = "Cadastra uma equipe no sistema para agrupar membros e projetos.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO create(@RequestBody @Valid CreationTeamDTO dto) {
        return teamService.create(dto);
    }

    @Operation(summary = "Listar todas as equipes", description = "Retorna uma lista de todas as equipes cadastradas.")
    @GetMapping
    public List<TeamDTO> list() {
        return teamService.listAll();
    }

    @Operation(summary = "Buscar equipe por ID", description = "Retorna os detalhes de uma equipe específica.")
    @GetMapping("/{id}")
    public TeamDTO get(@PathVariable Long id) {
        return teamService.getById(id);
    }

    @Operation(summary = "Atualizar equipe", description = "Edita as informações de uma equipe existente.")
    @PutMapping("/{id}")
    public TeamDTO update(@PathVariable Long id,
                          @RequestBody @Valid CreationTeamDTO dto) {
        return teamService.update(id, dto);
    }

    @Operation(summary = "Excluir equipe", description = "Remove permanentemente uma equipe do sistema.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        teamService.delete(id);
    }
}
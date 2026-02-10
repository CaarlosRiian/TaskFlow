package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.CreationTeamMemberDTO;
import com.taskflowproject.taskflow.dto.TeamMemberDTO;
import com.taskflowproject.taskflow.service.TeamMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/team-members")
@Tag(name = "Membros da Equipe", description = "Endpoints para associar membros de projetos a equipes específicas.")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @Operation(summary = "Adicionar membro à equipe", description = "Vincula um membro de projeto a uma equipe específica.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamMemberDTO add(@RequestBody @Valid CreationTeamMemberDTO dto) {
        return teamMemberService.add(dto);
    }

    @Operation(summary = "Listar membros de uma equipe", description = "Retorna todos os membros vinculados a uma equipe ID.")
    @GetMapping("/team/{teamId}")
    public List<TeamMemberDTO> listByTeam(@PathVariable Long teamId) {
        return teamMemberService.listByTeam(teamId);
    }

    @Operation(summary = "Remover membro da equipe", description = "Desvincula um membro de uma equipe (não o remove do projeto).")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        teamMemberService.remove(id);
    }
}
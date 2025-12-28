package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.CreationTeamMemberDTO;
import com.taskflowproject.taskflow.dto.TeamMemberDTO;
import com.taskflowproject.taskflow.service.TeamMemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/team-members")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamMemberDTO add(@RequestBody @Valid CreationTeamMemberDTO dto) {
        return teamMemberService.add(dto);
    }

    @GetMapping("/team/{teamId}")
    public List<TeamMemberDTO> listByTeam(@PathVariable Long teamId) {
        return teamMemberService.listByTeam(teamId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        teamMemberService.remove(id);
    }
}

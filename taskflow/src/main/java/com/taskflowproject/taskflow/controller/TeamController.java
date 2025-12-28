package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.CreationTeamDTO;
import com.taskflowproject.taskflow.dto.TeamDTO;
import com.taskflowproject.taskflow.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO create(@RequestBody @Valid CreationTeamDTO dto) {
        return teamService.create(dto);
    }

    @GetMapping
    public List<TeamDTO> list() {
        return teamService.listAll();
    }

    @GetMapping("/{id}")
    public TeamDTO get(@PathVariable Long id) {
        return teamService.getById(id);
    }

    @PutMapping("/{id}")
    public TeamDTO update(@PathVariable Long id,
                          @RequestBody @Valid CreationTeamDTO dto) {
        return teamService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        teamService.delete(id);
    }
}

package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.*;
import com.taskflowproject.taskflow.model.Team;
import com.taskflowproject.taskflow.model.User;
import com.taskflowproject.taskflow.repository.TeamRepository;
import com.taskflowproject.taskflow.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public TeamDTO create(CreationTeamDTO dto) {

        User leader = userRepository.findById(dto.leaderId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Líder não encontrado"));

        Team team = new Team();
        team.setName(dto.name());
        team.setDescription(dto.description());
        team.setLeader(leader);

        return toDTO(teamRepository.save(team));
    }

    public TeamDTO update(Long id, CreationTeamDTO dto) {

        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Time não encontrado"));

        User leader = userRepository.findById(dto.leaderId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Líder não encontrado"));

        team.setName(dto.name());
        team.setDescription(dto.description());
        team.setLeader(leader);

        return toDTO(teamRepository.save(team));
    }

    public List<TeamDTO> listAll() {
        return teamRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public TeamDTO getById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Time não encontrado"));
        return toDTO(team);
    }

    public void delete(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Time não encontrado");
        }
        teamRepository.deleteById(id);
    }

    private TeamDTO toDTO(Team team) {
        return new TeamDTO(
                team.getTeamId(),
                team.getName(),
                team.getDescription(),
                team.getLeader().getUserId()
        );
    }
}

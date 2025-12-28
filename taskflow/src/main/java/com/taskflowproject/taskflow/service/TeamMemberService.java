package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.*;
import com.taskflowproject.taskflow.model.*;
import com.taskflowproject.taskflow.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final RoleRepository roleRepository;

    public TeamMemberService(
            TeamMemberRepository teamMemberRepository,
            TeamRepository teamRepository,
            ProjectMemberRepository projectMemberRepository,
            RoleRepository roleRepository
    ) {
        this.teamMemberRepository = teamMemberRepository;
        this.teamRepository = teamRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.roleRepository = roleRepository;
    }

    public TeamMemberDTO add(CreationTeamMemberDTO dto) {

        if (teamMemberRepository.existsByTeamTeamIdAndProjectMemberProjectMemberId(
                dto.teamId(), dto.projectMemberId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Membro do projeto já faz parte deste time"
            );
        }

        Team team = teamRepository.findById(dto.teamId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Time não encontrado"));

        ProjectMember projectMember = projectMemberRepository.findById(dto.projectMemberId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Membro do projeto não encontrado"));

        Role role = roleRepository.findById(dto.roleId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Role não encontrada"));

        TeamMember member = new TeamMember();
        member.setTeam(team);
        member.setProjectMember(projectMember);
        member.setRole(role);

        return toDTO(teamMemberRepository.save(member));
    }

    public List<TeamMemberDTO> listByTeam(Long teamId) {
        return teamMemberRepository.findByTeamTeamId(teamId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public void remove(Long id) {
        if (!teamMemberRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Membro do time não encontrado");
        }
        teamMemberRepository.deleteById(id);
    }

    private TeamMemberDTO toDTO(TeamMember tm) {
        return new TeamMemberDTO(
                tm.getTeamMemberId(),
                tm.getTeam().getTeamId(),
                tm.getProjectMember().getProjectMemberId(),
                tm.getRole().getRoleId()
        );
    }
}

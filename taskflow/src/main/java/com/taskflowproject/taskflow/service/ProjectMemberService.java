package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.CreationProjectMemberDTO;
import com.taskflowproject.taskflow.dto.ProjectMemberDTO;
import com.taskflowproject.taskflow.model.Project;
import com.taskflowproject.taskflow.model.ProjectMember;
import com.taskflowproject.taskflow.model.Role;
import com.taskflowproject.taskflow.model.User;
import com.taskflowproject.taskflow.repository.ProjectMemberRepository;
import com.taskflowproject.taskflow.repository.ProjectRepository;
import com.taskflowproject.taskflow.repository.RoleRepository;
import com.taskflowproject.taskflow.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final RoleRepository roleRepository;

    public ProjectMemberService(
            ProjectMemberRepository projectMemberRepository,
            UserRepository userRepository,
            ProjectRepository projectRepository,
            RoleRepository roleRepository
    ) {
        this.projectMemberRepository = projectMemberRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.roleRepository = roleRepository;
    }

    public ProjectMemberDTO add(CreationProjectMemberDTO dto) {

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Project project = projectRepository.findById(dto.projectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado"));

        Role role = roleRepository.findById(dto.roleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Função não encontrada"));

        boolean exists = projectMemberRepository.existsByUserUserIdAndProjectProjectIdAndRoleRoleId(
                dto.userId(), dto.projectId(), dto.roleId()
        );

        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já é membro deste projeto com esta função");
        }

        ProjectMember member = new ProjectMember();
        member.setUser(user);
        member.setProject(project);
        member.setRole(role);

        return toDTO(projectMemberRepository.save(member));
    }

    public List<ProjectMemberDTO> listByProject(Long projectId) {
        return projectMemberRepository.findByProjectProjectId(projectId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public void remove(Long id) {
        if (!projectMemberRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membro do projeto não encontrado");
        }
        projectMemberRepository.deleteById(id);
    }

    private ProjectMemberDTO toDTO(ProjectMember pm) {
        return new ProjectMemberDTO(
                pm.getProjectMemberId(),
                pm.getUser().getUserId(),
                pm.getProject().getProjectId(),
                pm.getRole().getRoleId()
        );
    }
}

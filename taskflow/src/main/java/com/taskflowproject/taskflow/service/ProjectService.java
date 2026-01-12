package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.CreationProjectDTO;
import com.taskflowproject.taskflow.dto.ProjectDTO;
import com.taskflowproject.taskflow.model.*;
import com.taskflowproject.taskflow.repository.ProjectMemberRepository;
import com.taskflowproject.taskflow.repository.ProjectRepository;
import com.taskflowproject.taskflow.repository.RoleRepository;
import com.taskflowproject.taskflow.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public ProjectDTO createProject(CreationProjectDTO dto) {

        User manager = userRepository.findById(dto.managerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manager Not Found."));

        Project project = new Project();
        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setStartDate(dto.startDate());
        project.setEndDate(dto.endDate());
        project.setManager(manager);
        project.setStatus(dto.status());

        projectRepository.save(project);

        Role managerRole = roleRepository.findByName("GERENTE")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role 'GERENTE' / Manager Not Found."));

        ProjectMember pm = new ProjectMember();
        pm.setProject(project);
        pm.setUser(manager);
        pm.setRole(managerRole);
        projectMemberRepository.save(pm);

        return new ProjectDTO(
                project.getProjectId(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getStatus().name(),
                project.getManager().getUserId()
        );
    }

    public List<ProjectDTO> listProjectsForUser(Long userId) {

        return projectMemberRepository.findByUserUserId(userId)
                .stream()
                .map(pm -> {
                    Project p = pm.getProject();
                    return new ProjectDTO(
                            p.getProjectId(),
                            p.getName(),
                            p.getDescription(),
                            p.getStartDate(),
                            p.getEndDate(),
                            p.getStatus().name(),
                            p.getManager().getUserId()
                    );
                })
                .toList();
    }

    @Transactional
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found."));
        return new ProjectDTO(
                project.getProjectId(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getStatus().name(),
                project.getManager().getUserId()
        );
    }

    @Transactional
    public ProjectDTO updateProject(Long id, CreationProjectDTO dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found."));

        User manager = userRepository.findById(dto.managerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manager Not Found."));

        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setStartDate(dto.startDate());
        project.setEndDate(dto.endDate());
        project.setManager(manager);
        project.setStatus(dto.status());

        return new ProjectDTO(
                project.getProjectId(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getStatus().name(),
                project.getManager().getUserId()
        );
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found."));
        projectRepository.delete(project);
    }
}

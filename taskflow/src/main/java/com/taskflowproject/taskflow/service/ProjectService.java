package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.ProjectDTO;
import com.taskflowproject.taskflow.dto.CreationProjectDTO;
import com.taskflowproject.taskflow.model.*;
import com.taskflowproject.taskflow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Transactional
    @CacheEvict(value = "projects", allEntries = true)
    public ProjectDTO createProject(CreationProjectDTO dto) {
        User manager = userRepository.findById(dto.managerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gerente Não Encontrado."));

        Project project = new Project();
        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setStartDate(dto.startDate());
        project.setEndDate(dto.endDate());
        project.setStatus(dto.status());
        project.setManager(manager);

        Project savedProject = projectRepository.save(project);

        Role gerenteRole = roleRepository.findByName("GERENTE")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "A função GERENTE não foi encontrada, Cadastre-a primeiro!"));

        ProjectMember membership = new ProjectMember();
        membership.setProject(savedProject);
        membership.setUser(manager);
        membership.setRole(gerenteRole);
        projectMemberRepository.save(membership);

        return mapToDTO(savedProject);
    }

    @Cacheable(value = "projects")
    public List<ProjectDTO> listProjectsForUser(Long userId) {
        return projectRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "projects", key = "#id")
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));
        return mapToDTO(project);
    }

    @Transactional
    @CacheEvict(value = "projects", allEntries = true)
    public ProjectDTO updateProject(Long id, CreationProjectDTO dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));

        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setStartDate(dto.startDate());
        project.setEndDate(dto.endDate());
        project.setStatus(dto.status());

        return mapToDTO(projectRepository.save(project));
    }

    @Transactional
    @CacheEvict(value = "projects", allEntries = true)
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));

        projectMemberRepository.deleteByProject(project);
        projectRepository.delete(project);
    }

    @Transactional
    @CacheEvict(value = "projects", allEntries = true)
    public void addMemberToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado ID: " + projectId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado ID: " + userId));

        Role userRole = roleRepository.findByName("GERENTE")
                .orElseGet(() -> roleRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhuma Role cadastrada no banco!")));

        ProjectMember member = new ProjectMember();
        member.setProject(project);
        member.setUser(user);
        member.setRole(userRole);

        projectMemberRepository.save(member);
    }

    private ProjectDTO mapToDTO(Project project) {
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
}
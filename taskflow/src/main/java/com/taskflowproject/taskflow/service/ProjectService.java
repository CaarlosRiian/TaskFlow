package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.CreationProjectDTO;
import com.taskflowproject.taskflow.dto.ProjectDTO;
import com.taskflowproject.taskflow.model.*;
import com.taskflowproject.taskflow.repository.ProjectRepository;
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
    private UserRepository userRepository;

    @Transactional
    public ProjectDTO createProject(CreationProjectDTO dto) {

        User manager = userRepository.findById(dto.managerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gerente não encontrado."));

        Project project = new Project();
        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setStartDate(dto.startDate());
        project.setEndDate(dto.endDate());
        project.setManager(manager);

        project.setStatus(dto.status());

        projectRepository.save(project);

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

    public List<ProjectDTO> listProjects() {
        return projectRepository.findAll().stream()
                .map(p -> new ProjectDTO(
                        p.getProjectId(),
                        p.getName(),
                        p.getDescription(),
                        p.getStartDate(),
                        p.getEndDate(),
                        p.getStatus().name(),
                        p.getManager().getUserId()
                ))
                .toList();
    }

    @Transactional
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));

        User manager = userRepository.findById(dto.managerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gerente não encontrado."));

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));
        projectRepository.delete(project);
    }
}

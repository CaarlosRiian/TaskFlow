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
        project.setStart_date(dto.startDate());
        project.setEnd_date(dto.endDate());
        project.setManager(manager);

        project.setStatus(dto.status());

        projectRepository.save(project);

        return new ProjectDTO(
                project.getProject_id(),
                project.getName(),
                project.getDescription(),
                project.getStart_date(),
                project.getEnd_date(),
                project.getStatus().name(),
                project.getManager().getUser_id()
        );
    }

    public List<ProjectDTO> listProjects() {
        return projectRepository.findAll().stream()
                .map(p -> new ProjectDTO(
                        p.getProject_id(),
                        p.getName(),
                        p.getDescription(),
                        p.getStart_date(),
                        p.getEnd_date(),
                        p.getStatus().name(),
                        p.getManager().getUser_id()
                ))
                .toList();
    }

    @Transactional
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));
        return new ProjectDTO(
                project.getProject_id(),
                project.getName(),
                project.getDescription(),
                project.getStart_date(),
                project.getEnd_date(),
                project.getStatus().name(),
                project.getManager().getUser_id()
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
        project.setStart_date(dto.startDate());
        project.setEnd_date(dto.endDate());
        project.setManager(manager);
        project.setStatus(dto.status());

        return new ProjectDTO(
                project.getProject_id(),
                project.getName(),
                project.getDescription(),
                project.getStart_date(),
                project.getEnd_date(),
                project.getStatus().name(),
                project.getManager().getUser_id()
        );
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));
        projectRepository.delete(project);
    }
}

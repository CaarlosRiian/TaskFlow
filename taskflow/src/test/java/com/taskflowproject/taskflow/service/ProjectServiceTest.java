package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.CreationProjectDTO;
import com.taskflowproject.taskflow.dto.ProjectDTO;
import com.taskflowproject.taskflow.model.Project;
import com.taskflowproject.taskflow.model.Role;
import com.taskflowproject.taskflow.model.User;
import com.taskflowproject.taskflow.repository.ProjectMemberRepository;
import com.taskflowproject.taskflow.repository.ProjectRepository;
import com.taskflowproject.taskflow.repository.RoleRepository;
import com.taskflowproject.taskflow.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateProjectSuccessfully() {
        User manager = new User();
        manager.setUserId(1L);

        Role roleGerente = new Role();
        roleGerente.setName("GERENTE");
        when(roleRepository.findByName("GERENTE")).thenReturn(Optional.of(roleGerente));

        CreationProjectDTO dto = new CreationProjectDTO(
                "Projeto Alsol",
                "Descrição",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(10),
                1L,
                Project.Status.DESENVOLVIMENTO
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(manager));

        Project savedProject = new Project();
        savedProject.setProjectId(10L);
        savedProject.setName(dto.name());
        savedProject.setManager(manager);
        savedProject.setStatus(dto.status());

        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);

        when(projectMemberRepository.save(any())).thenReturn(null);

        ProjectDTO result = projectService.createProject(dto);

        assertNotNull(result);
        assertEquals("Projeto Alsol", result.name());
        assertEquals(1L, result.managerId());

        verify(projectRepository).save(any());
        verify(projectMemberRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenManagerNotFound() {
        CreationProjectDTO dto = new CreationProjectDTO(
                "Projeto Alsol",
                "Descrição",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(10),
                99L,
                Project.Status.DESENVOLVIMENTO
        );

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> projectService.createProject(dto));
    }
}
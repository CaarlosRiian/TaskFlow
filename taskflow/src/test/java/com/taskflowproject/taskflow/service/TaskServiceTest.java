package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.TaskDTO;
import com.taskflowproject.taskflow.model.Project;
import com.taskflowproject.taskflow.model.Task;
import com.taskflowproject.taskflow.model.User;
import com.taskflowproject.taskflow.repository.ProjectRepository;
import com.taskflowproject.taskflow.repository.TaskRepository;
import com.taskflowproject.taskflow.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnTaskById() {
        User user = new User();
        user.setUserId(1L);

        Project project = new Project();
        project.setProjectId(1L);

        Task task = new Task();
        task.setTaskId(1L);
        task.setTitle("Teste");
        task.setAssignedTo(user);
        task.setProject(project);
        task.setStatus(Task.Status.PENDENTE);
        task.setPriority(Task.Priority.ALTA);
        task.setCreationDate(LocalDateTime.now());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDTO result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Teste", result.title());
    }

    @Test
    void shouldDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        assertDoesNotThrow(() -> taskService.deleteTask(1L));

        verify(taskRepository, times(1)).deleteById(1L);
    }
}

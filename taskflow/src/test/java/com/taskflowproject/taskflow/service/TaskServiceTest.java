package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.model.Task;
import com.taskflowproject.taskflow.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveTaskSuccessfully() {
        Task task = new Task();
        task.setTask_id(1L);
        task.setTitle("Nova Tarefa Nadic");

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.saveTask(task);

        assertEquals(1L, result.getTask_id());
        assertEquals("Nova Tarefa Nadic", result.getTitle());
    }

    @Test
    void shouldReturnTaskById() {
        Task task = new Task();
        task.setTask_id(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getTask_id());
    }

    @Test
    void shouldDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        assertDoesNotThrow(() -> taskService.deleteTask(1L));

        verify(taskRepository, times(1)).deleteById(1L);
    }
}

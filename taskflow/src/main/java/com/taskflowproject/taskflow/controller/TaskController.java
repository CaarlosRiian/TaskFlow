package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.CreationTaskDTO;
import com.taskflowproject.taskflow.dto.TaskDTO;
import com.taskflowproject.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid CreationTaskDTO dto) {
        return taskService.createTask(dto);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(
            @PathVariable Long id,
            @RequestBody @Valid CreationTaskDTO dto
    ) {
        return taskService.updateTask(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}

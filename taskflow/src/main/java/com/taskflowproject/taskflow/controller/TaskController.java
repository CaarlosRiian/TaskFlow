package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.CreationTaskDTO;
import com.taskflowproject.taskflow.dto.TaskDTO;
import com.taskflowproject.taskflow.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tarefas", description = "Endpoints para criação, edição e consulta de tarefas dos projetos.")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Listar todas as tarefas", description = "Retorna uma lista de todas as tarefas cadastradas no sistema.")
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Buscar tarefa por ID", description = "Retorna os detalhes de uma tarefa específica.")
    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Criar nova tarefa", description = "Cadastra uma nova tarefa vinculada a um projeto e a um usuário responsável.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@RequestBody @Valid CreationTaskDTO dto) {
        return taskService.createTask(dto);
    }

    @Operation(summary = "Atualizar tarefa", description = "Edita as informações de uma tarefa existente | título, descrição, prazo, etc. |.")
    @PutMapping("/{id}")
    public TaskDTO updateTask(
            @PathVariable Long id,
            @RequestBody @Valid CreationTaskDTO dto
    ) {
        return taskService.updateTask(id, dto);
    }

    @Operation(summary = "Excluir tarefa", description = "Remove permanentemente uma tarefa do banco de dados.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
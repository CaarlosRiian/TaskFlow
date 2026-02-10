package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.CreationTaskDTO;
import com.taskflowproject.taskflow.dto.TaskDTO;
import com.taskflowproject.taskflow.model.Project;
import com.taskflowproject.taskflow.model.Task;
import com.taskflowproject.taskflow.model.User;
import com.taskflowproject.taskflow.repository.ProjectRepository;
import com.taskflowproject.taskflow.repository.TaskRepository;
import com.taskflowproject.taskflow.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Cacheable(value = "tasks")
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Cacheable(value = "tasks", key = "#id")
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        return toDTO(task);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public TaskDTO createTask(CreationTaskDTO dto) {
        User user = userRepository.findById(dto.assignedTo())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Project project = projectRepository.findById(dto.projectId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        if (project.getStatus() == Project.Status.CONCLUIDO ||
                project.getStatus() == Project.Status.CANCELADO) {
            throw new RuntimeException("Não é possível criar tarefas em projetos finalizados");
        }

        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setCreationDate(LocalDateTime.now());
        task.setDueDate(dto.dueDate());
        task.setAssignedTo(user);
        task.setProject(project);
        task.setStatus(Task.Status.PENDENTE);
        task.setPriority(Task.Priority.valueOf(dto.priority()));

        Task savedTask = taskRepository.save(task);
        return toDTO(savedTask);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public TaskDTO updateTask(Long id, CreationTaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        User user = userRepository.findById(dto.assignedTo())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Project project = projectRepository.findById(dto.projectId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setDueDate(dto.dueDate());
        task.setAssignedTo(user);
        task.setProject(project);
        task.setPriority(Task.Priority.valueOf(dto.priority()));

        Task updatedTask = taskRepository.save(task);
        return toDTO(updatedTask);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada");
        }
        taskRepository.deleteById(id);
    }

    private TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getTaskId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getCreationDate(),
                task.getDueDate(),
                task.getAssignedTo().getUserId(),
                task.getProject().getProjectId()
        );
    }
}

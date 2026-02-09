package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.*;
import com.taskflowproject.taskflow.model.Comment;
import com.taskflowproject.taskflow.model.*;
import com.taskflowproject.taskflow.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public CommentService(
            CommentRepository commentRepository,
            UserRepository userRepository,
            TaskRepository taskRepository
    ) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    @CacheEvict(value = "comments", allEntries = true)
    public CommentDTO create(CreationCommentDTO dto) {
        User author = userRepository.findById(dto.authorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        Task task = taskRepository.findById(dto.taskId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        Comment comment = new Comment();
        comment.setMessage(dto.message());
        comment.setAuthor(author);
        comment.setTask(task);
        comment.setDateTime(LocalDateTime.now());

        Comment saved = commentRepository.save(comment);

        return toDTO(saved);
    }

    @Cacheable(value = "comments")
    public List<CommentDTO> getAll() {
        return commentRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Cacheable(value = "comments", key = "#id")
    public CommentDTO getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
        return toDTO(comment);
    }

    @Transactional
    @CacheEvict(value = "comments", allEntries = true)
    public CommentDTO update(Long id, UpdateCommentDTO dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));

        comment.setMessage(dto.message());
        comment.setDateTime(LocalDateTime.now());

        Comment updated = commentRepository.save(comment);

        return toDTO(updated);
    }

    @Transactional
    @CacheEvict(value = "comments", allEntries = true)
    public void delete(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comentário não existe");
        }
        commentRepository.deleteById(id);
    }

    private CommentDTO toDTO(Comment c) {
        return new CommentDTO(
                c.getCommentId(),
                c.getMessage(),
                c.getDateTime(),
                c.getAuthor().getUserId(),
                c.getTask().getTaskId()
        );
    }
}

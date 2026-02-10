package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.*;
import com.taskflowproject.taskflow.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comentários", description = "Endpoints para interação e feedback em tarefas.")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @Operation(summary = "Listar todos os comentários", description = "Retorna uma lista de todos os comentários registrados no sistema.")
    @GetMapping
    public List<CommentDTO> getAllComments(){
        return commentService.getAll();
    }

    @Operation(summary = "Buscar comentário por ID", description = "Retorna os detalhes de um comentário específico.")
    @GetMapping("/{id}")
    public CommentDTO getCommentById(@PathVariable Long id){
        return commentService.getById(id);
    }

    @Operation(summary = "Criar novo comentário", description = "Adiciona um comentário a uma tarefa, Requer o ID do autor e da tarefa.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(@RequestBody @Valid CreationCommentDTO dto){
        return commentService.create(dto);
    }

    @Operation(summary = "Atualizar comentário", description = "Permite editar o conteúdo de um comentário existente.")
    @PutMapping("/{id}")
    public CommentDTO updateComment(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCommentDTO dto) {
        return commentService.update(id, dto);
    }

    @Operation(summary = "Excluir comentário", description = "Remove permanentemente um comentário do sistema.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id){
        commentService.delete(id);
    }
}
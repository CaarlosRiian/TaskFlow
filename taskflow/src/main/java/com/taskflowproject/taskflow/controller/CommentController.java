package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.*;
import com.taskflowproject.taskflow.service.CommentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentDTO> getAllComments(){
        return commentService.getAll();
    }

    @GetMapping("/{id}")
    public CommentDTO getCommentById(@PathVariable Long id){
        return commentService.getById(id);
    }

    @PostMapping
    public CommentDTO createComment(@RequestBody @Valid CreationCommentDTO dto){
        return commentService.create(dto);
    }

    @PutMapping("/{id}")
    public CommentDTO updateComment(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCommentDTO dto) {

        return commentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){
        commentService.delete(id);
    }
}

package com.taskflowproject.taskflow.dto;

public record UserDTO (
    Long id,
    String name,
    String email,
    boolean active
){}

package com.taskflowproject.taskflow.dto;

public record TeamDTO(

        Long teamId,
        String name,
        String description,
        Long leaderId

) {}

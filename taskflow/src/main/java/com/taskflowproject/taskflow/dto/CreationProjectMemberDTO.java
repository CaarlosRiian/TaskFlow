package com.taskflowproject.taskflow.dto;

import jakarta.validation.constraints.NotNull;

public record CreationProjectMemberDTO(

        @NotNull
        Long userId,

        @NotNull
        Long projectId,

        @NotNull
        Long roleId

) {}

package com.taskflowproject.taskflow.dto;

import jakarta.validation.constraints.NotNull;

public record CreationTeamMemberDTO(

        @NotNull
        Long teamId,

        @NotNull
        Long projectMemberId,

        @NotNull
        Long roleId

) {}

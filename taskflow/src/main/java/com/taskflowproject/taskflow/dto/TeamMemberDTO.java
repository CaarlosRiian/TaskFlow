package com.taskflowproject.taskflow.dto;

public record TeamMemberDTO(

        Long teamMemberId,
        Long teamId,
        Long projectMemberId,
        Long roleId

) {}

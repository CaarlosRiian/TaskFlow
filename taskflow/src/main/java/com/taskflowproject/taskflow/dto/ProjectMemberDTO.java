package com.taskflowproject.taskflow.dto;

public record ProjectMemberDTO(

        Long projectMemberId,
        Long userId,
        Long projectId,
        Long roleId

) {}

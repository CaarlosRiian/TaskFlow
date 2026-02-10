package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.model.ProjectMember;
import com.taskflowproject.taskflow.model.Role;
import com.taskflowproject.taskflow.repository.ProjectMemberRepository;
import com.taskflowproject.taskflow.repository.TeamMemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PermissionService {

    private static final String ROLE_PROJECT_MANAGER = "PROJECT_MANAGER";
    private static final String ROLE_DEV_SENIOR = "DEV_SENIOR";

    private final ProjectMemberRepository projectMemberRepository;
    private final TeamMemberRepository teamMemberRepository;

    public PermissionService(ProjectMemberRepository projectMemberRepository,
                             TeamMemberRepository teamMemberRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    public ProjectMember getProjectMemberOrThrow(Long userId, Long projectId) {
        return projectMemberRepository
                .findByUserUserIdAndProjectProjectId(userId, projectId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.FORBIDDEN,
                                "Usuário não é um membro deste projeto!"
                        ));
    }

    public boolean isProjectManager(ProjectMember projectMember) {
        return hasRole(projectMember, ROLE_PROJECT_MANAGER);
    }

    public boolean isSeniorDeveloper(ProjectMember projectMember) {
        return hasRole(projectMember, ROLE_DEV_SENIOR);
    }

    public boolean canCreateTask(ProjectMember projectMember) {
        return isProjectManager(projectMember) || isSeniorDeveloper(projectMember);
    }

    public boolean canAddProjectMember(ProjectMember projectMember) {
        return isProjectManager(projectMember);
    }

    public boolean canAddTeamMember(ProjectMember projectMember) {
        return isProjectManager(projectMember) || isSeniorDeveloper(projectMember);
    }

    public boolean canComment(ProjectMember projectMember) {
        return projectMember != null;
    }

    public boolean isMemberOfTeam(Long teamId, Long projectMemberId) {
        return teamMemberRepository
                .existsByTeamTeamIdAndProjectMemberProjectMemberId(
                        teamId, projectMemberId
                );
    }

    private boolean hasRole(ProjectMember projectMember, String roleName) {
        Role role = projectMember.getRole();
        return role != null && roleName.equalsIgnoreCase(role.getName());
    }
}

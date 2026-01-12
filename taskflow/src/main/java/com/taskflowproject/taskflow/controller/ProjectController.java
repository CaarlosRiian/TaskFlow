package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.*;
import com.taskflowproject.taskflow.service.PermissionService;
import com.taskflowproject.taskflow.service.ProjectService;

import com.taskflowproject.taskflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ProjectDTO createProject(@RequestBody @Valid CreationProjectDTO dto) {
        return projectService.createProject(dto);
    }

    @GetMapping
    public List<ProjectDTO> listProjects(Authentication authentication) {
        Long userId = userService.getUserIdFromAuth(authentication);

        return projectService.listProjectsForUser(userId);
    }


    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Long id, Authentication authentication) {
        Long userId = userService.getUserIdFromAuth(authentication);
        permissionService.getProjectMemberOrThrow(userId, id);

        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody @Valid CreationProjectDTO dto) {
        return projectService.updateProject(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

}

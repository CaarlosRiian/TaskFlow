package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.CreationProjectMemberDTO;
import com.taskflowproject.taskflow.dto.ProjectMemberDTO;
import com.taskflowproject.taskflow.dto.UpdateProjectMemberRoleDTO;
import com.taskflowproject.taskflow.service.ProjectMemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/project-members")
public class ProjectMemberController {  

    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectMemberDTO add(@RequestBody @Valid CreationProjectMemberDTO dto) {
        return projectMemberService.add(dto);
    }

    @GetMapping("/project/{projectId}")
    public List<ProjectMemberDTO> listByProject(@PathVariable Long projectId) {
        return projectMemberService.listByProject(projectId);
    }

    @PutMapping("/{id}/role")
    public ProjectMemberDTO updateRole(
            @PathVariable Long id,
            @RequestBody UpdateProjectMemberRoleDTO dto
    ) {
        return projectMemberService.updateRole(id, dto.roleId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        projectMemberService.remove(id);
    }
}

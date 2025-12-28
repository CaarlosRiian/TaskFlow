package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.CreationRoleDTO;
import com.taskflowproject.taskflow.dto.RoleDTO;
import com.taskflowproject.taskflow.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO create(@RequestBody @Valid CreationRoleDTO dto) {
        return roleService.create(dto);
    }

    @PutMapping("/{id}")
    public RoleDTO updateRole(@PathVariable Long id, @RequestBody @Valid CreationRoleDTO dto) {
        return roleService.updateRole(id, dto);
    }

    @GetMapping
    public List<RoleDTO> list() {
        return roleService.listAll();
    }

    @GetMapping("/{id}")
    public RoleDTO getRole(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        roleService.delete(id);
    }

}

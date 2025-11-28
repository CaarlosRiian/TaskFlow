package com.taskflowproject.taskflow.controller;

import com.taskflowproject.taskflow.dto.*;
import com.taskflowproject.taskflow.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDTO createUser(@RequestBody @Valid CreationUserDTO dto) {
        return userService.createUser(dto);
    }

    @GetMapping
    public List<UserDTO> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody @Valid CreationUserDTO dto) {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}/toggle")
    public UserDTO toggleUserActive(@PathVariable Long id) {
        return userService.toggleUserActive(id);
    }
}

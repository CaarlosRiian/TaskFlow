package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.UserDTO;
import com.taskflowproject.taskflow.dto.CreationUserDTO;
import com.taskflowproject.taskflow.model.User;
import com.taskflowproject.taskflow.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public UserDTO createUser(CreationUserDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado.");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setActive(true);

        userRepository.save(user);

        return new UserDTO(
                user.getUser_id(),
                user.getName(),
                user.getEmail(),
                user.isActive()
        );
    }

    public List<UserDTO> listUsers() {
        return userRepository.findAll().stream()
                .map(u -> new UserDTO(
                        u.getUser_id(),
                        u.getName(),
                        u.getEmail(),
                        u.isActive()
                ))
                .toList();
    }

    @Transactional
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return new UserDTO(user.getUser_id(), user.getName(), user.getEmail(), user.isActive());
    }

    @Transactional
    public UserDTO updateUser(Long id, CreationUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (!user.getEmail().equals(dto.email()) && userRepository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado.");
        }

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));

        return new UserDTO(user.getUser_id(), user.getName(), user.getEmail(), user.isActive());
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        userRepository.delete(user);
    }

    @Transactional
    public UserDTO toggleUserActive(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        user.setActive(!user.isActive());
        return new UserDTO(user.getUser_id(), user.getName(), user.getEmail(), user.isActive());
    }
}

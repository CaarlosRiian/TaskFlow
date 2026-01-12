package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.UserDTO;
import com.taskflowproject.taskflow.dto.CreationUserDTO;
import com.taskflowproject.taskflow.model.User;
import com.taskflowproject.taskflow.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createUser(CreationUserDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered!");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setActive(true);

        userRepository.save(user);

        return mapToDTO(user);
    }

    public List<UserDTO> listUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional
    public UserDTO getUserById(Long id) {
        User user = findUserOrThrow(id);
        return mapToDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, CreationUserDTO dto) {
        User user = findUserOrThrow(id);

        if (!user.getEmail().equals(dto.email()) && userRepository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered!");
        }

        user.setName(dto.name());
        user.setEmail(dto.email());

        if (dto.password() != null && !dto.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }

        return mapToDTO(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = findUserOrThrow(id);
        userRepository.delete(user);
    }

    @Transactional
    public UserDTO toggleUserActive(Long id) {
        User user = findUserOrThrow(id);
        user.setActive(!user.isActive());
        return mapToDTO(user);
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(user.getUserId(), user.getName(), user.getEmail(), user.isActive());
    }

    public Long getUserIdFromAuth(Authentication auth) {
        return findByEmail(auth.getName()).getUserId();
    }

}

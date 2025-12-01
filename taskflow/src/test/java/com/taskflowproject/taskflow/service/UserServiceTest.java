package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.CreationUserDTO;
import com.taskflowproject.taskflow.dto.UserDTO;
import com.taskflowproject.taskflow.model.User;
import com.taskflowproject.taskflow.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        CreationUserDTO dto = new CreationUserDTO("Carlos Eduardo", "carlos@mail.com", "1234edu");

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);

        User savedUser = new User(1L, "Carlos Eduardo", "carlos@mail.com", "encoded", true, null, null, null, null, null);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals("Carlos Eduardo", result.name());
        assertEquals("carlos@mail.com", result.email());
    }

    @Test
    void shouldNotCreateUserWithDuplicateEmail() {
        CreationUserDTO dto = new CreationUserDTO("Carlos Eduardo", "carlos@mail.com", "1234edu");

        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> userService.createUser(dto));
    }

    @Test
    void shouldListAllUsers() {
        User user = new User(1L, "Jeferson Queiroga", "jefdoif@mail.com", "queirogax", true, null, null, null, null, null);
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> list = userService.listUsers();

        assertEquals(1, list.size());
        assertEquals("Jeferson Queiroga", list.get(0).name());
    }
}

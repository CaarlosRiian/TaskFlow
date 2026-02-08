package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.LoginRequestDTO;
import com.taskflowproject.taskflow.dto.LoginResponseDTO;
import com.taskflowproject.taskflow.model.User;
import com.taskflowproject.taskflow.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        // Como o jwtSecret vem do @Value, precisamos injetar manualmente no teste
        ReflectionTestUtils.setField(authService, "jwtSecret", "minhaSuperChaveSecretaMuitoLongaParaJWT12345");
    }

    @Test
    @DisplayName("Deve realizar login com sucesso quando credenciais forem válidas")
    void loginSuccess() {
        // Arrange
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("teste@taskflow.com");
        request.setPassword("123456");

        User user = new User();
        user.setEmail("teste@taskflow.com");
        user.setPassword("senhaCriptografada");
        user.setName("Usuario Teste");
        user.setRoles(new ArrayList<>());

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);

        // Act
        LoginResponseDTO response = authService.login(request);

        // Assert
        assertNotNull(response.getToken());
        assertEquals("Usuario Teste", response.getName());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha estiver incorreta")
    void loginInvalidPassword() {
        // Arrange
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("teste@taskflow.com");
        request.setPassword("senha_errada");

        User user = new User();
        user.setPassword("senha_correta_criptografada");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.login(request));
    }
}
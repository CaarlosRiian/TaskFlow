package com.taskflowproject.taskflow.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskflowproject.taskflow.dto.LoginRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 403 ao acessar rota protegida sem token")
    void shouldReturnForbiddenWithoutToken() throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve retornar 401 ao tentar login com credenciais inválidas")
    void shouldReturnUnauthorizedWithInvalidCredentials() throws Exception {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("pumbatim@email.com");
        login.setPassword("123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isBadRequest());
    }
}
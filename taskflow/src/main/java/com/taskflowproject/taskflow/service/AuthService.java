package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.LoginRequestDTO;
import com.taskflowproject.taskflow.dto.LoginResponseDTO;
import com.taskflowproject.taskflow.model.User;
import com.taskflowproject.taskflow.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String jwtSecret = "minhaSuperChaveSecretaMuitoLongaParaJWT12345";

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(loginRequest.getEmail()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = generateToken(user);

        return new LoginResponseDTO(token, user.getEmail(), user.getName());
    }

    private String generateToken(User user) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("cargo", user.getRoles().isEmpty() ? "N/A" : user.getRoles().get(0).getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}

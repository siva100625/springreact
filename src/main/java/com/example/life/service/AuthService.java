package com.example.life.service;

import com.example.life.model.*;
import com.example.life.repository.UserDetailsRepository;
import com.example.life.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserDetailsRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtProvider;

    public AuthResponse register(UserDetailsDto dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        UserEntity user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of("USER"));
        repository.save(user);

        String token = jwtProvider.generateToken(user.getEmail(), user.getRoles());
        return new AuthResponse(token);
    }

    public AuthResponse login(UserDetailsDto dto) {
        UserEntity user = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtProvider.generateToken(user.getEmail(), user.getRoles());
        return new AuthResponse(token);
    }
}

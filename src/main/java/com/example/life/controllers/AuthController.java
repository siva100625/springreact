package com.example.life.controllers;

import com.example.life.model.*;
import com.example.life.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserDetailsDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserDetailsDto dto) {
        return authService.login(dto);
    }
}

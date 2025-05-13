package com.senac.pizzademo.controller;

import com.senac.pizzademo.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");

        if ("admin".equals(username) && "senha123".equals(password)) {
            String token = JwtUtil.generateToken(username);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        } else {
            throw new RuntimeException("Usuário ou senha inválidos.");
        }
    }
}

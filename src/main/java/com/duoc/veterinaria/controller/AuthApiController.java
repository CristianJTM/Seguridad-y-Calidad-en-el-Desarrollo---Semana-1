package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.config.JwtUtil;
import com.duoc.veterinaria.model.usuario.Usuario;
import com.duoc.veterinaria.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthApiController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        if (usuario == null || !passwordEncoder.matches(password, usuario.getPasswordHash())) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        }

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRole());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", usuario.getUsername(),
                "role", usuario.getRole()
        ));
    }
}

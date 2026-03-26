package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.config.JwtUtil;
import com.duoc.veterinaria.model.usuario.Usuario;
import com.duoc.veterinaria.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller REST para autenticación con JWT.
 *
 * Endpoints:
 * - POST /api/auth/login: Iniciar sesión y obtener token JWT
 * - POST /api/auth/register: Registrar nuevo usuario
 */
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

    /**
     * Endpoint para iniciar sesión.
     *
     * Flujo:
     * 1. Recibe credenciales (username, password)
     * 2. Valida el usuario en la base de datos
     * 3. Verifica la contraseña con BCrypt
     * 4. Genera un token JWT
     * 5. Retorna el token junto con información del usuario
     *
     * Request body:
     * {
     *   "username": "usuario",
     *   "password": "contraseña"
     * }
     *
     * Response success (200):
     * {
     *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     *   "username": "usuario",
     *   "role": "USER"
     * }
     *
     * Response error (401):
     * {
     *   "error": "Credenciales inválidas"
     * }
     *
     * @param credentials Mapa con username y password
     * @return ResponseEntity con token y datos del usuario o error
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Buscar usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        // Validar credenciales
        if (usuario == null || !passwordEncoder.matches(password, usuario.getPasswordHash())) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        }

        // Generar token JWT
        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRole());

        // Retornar token y datos del usuario
        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", usuario.getUsername(),
                "role", usuario.getRole()
        ));
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * Flujo:
     * 1. Recibe datos del nuevo usuario (username, password, fullname, email)
     * 2. Valida que el usuario no exista
     * 3. Encripta la contraseña con BCrypt
     * 4. Crea el usuario con rol USER por defecto
     * 5. Guarda en la base de datos
     *
     * Request body:
     * {
     *   "username": "nuevo_usuario",
     *   "password": "contraseña_segura",
     *   "fullname": "Nombre Completo",
     *   "email": "email@ejemplo.com"
     * }
     *
     * Response success (200):
     * {
     *   "message": "Usuario registrado correctamente",
     *   "username": "nuevo_usuario"
     * }
     *
     * Response error (400):
     * {
     *   "message": "El usuario ya existe"
     * }
     *
     * @param userData Mapa con los datos del nuevo usuario
     * @return ResponseEntity con mensaje de éxito o error
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String password = userData.get("password");
        String fullname = userData.get("fullname");
        String email = userData.get("email");

        // Validar que el usuario no exista
        if (usuarioRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.status(400).body(Map.of("message", "El usuario ya existe"));
        }

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setPasswordHash(passwordEncoder.encode(password)); // Encriptar contraseña
        nuevoUsuario.setRole("USER"); // Rol por defecto

        // Guardar en base de datos
        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok(Map.of(
                "message", "Usuario registrado correctamente",
                "username", username
        ));
    }
}

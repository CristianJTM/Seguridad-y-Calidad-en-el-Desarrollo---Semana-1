package com.duoc.veterinaria.service;

import com.duoc.veterinaria.model.usuario.Usuario;
import com.duoc.veterinaria.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerVeterinarios() {
        return usuarioRepository.findByRoleIn(List.of("VET", "ADMIN"));
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}

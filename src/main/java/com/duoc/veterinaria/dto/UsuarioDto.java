package com.duoc.veterinaria.dto;

import com.duoc.veterinaria.model.usuario.Usuario;

public record UsuarioDto(Long id, String username, String role) {

    public static UsuarioDto from(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDto(usuario.getId(), usuario.getUsername(), usuario.getRole());
    }
}

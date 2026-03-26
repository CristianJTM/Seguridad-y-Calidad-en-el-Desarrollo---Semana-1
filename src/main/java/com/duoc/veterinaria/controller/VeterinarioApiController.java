package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.dto.UsuarioDto;
import com.duoc.veterinaria.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioApiController {

    private final UsuarioService usuarioService;

    public VeterinarioApiController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listarVeterinarios() {
        List<UsuarioDto> vets = usuarioService.obtenerVeterinarios().stream()
                .map(UsuarioDto::from)
                .toList();
        return ResponseEntity.ok(vets);
    }
}

package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteApiController {

    private final PacienteService pacienteService;

    public PacienteApiController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(pacienteService.obtenerPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtener(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente);
    }

    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody Paciente paciente) {
        pacienteService.guardarPaciente(paciente);
        return ResponseEntity.status(201).body(paciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(@PathVariable Long id, @RequestBody Paciente datos) {
        Paciente paciente = pacienteService.buscarPorId(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        paciente.setNombre(datos.getNombre());
        paciente.setEspecie(datos.getEspecie());
        paciente.setRaza(datos.getRaza());
        paciente.setEdad(datos.getEdad());
        paciente.setDueno(datos.getDueno());
        pacienteService.guardarPaciente(paciente);
        return ResponseEntity.ok(paciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}

package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.model.cita.Cita;
import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.service.CitaService;
import com.duoc.veterinaria.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
public class CitaApiController {

    private final CitaService citaService;
    private final PacienteService pacienteService;

    public CitaApiController(CitaService citaService, PacienteService pacienteService) {
        this.citaService = citaService;
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(citaService.obtenerCitas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtener(@PathVariable Long id) {
        Cita cita = citaService.buscarPorId(id);
        if (cita == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cita);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> datos, Principal principal) {
        Long pacienteId = Long.valueOf(datos.get("pacienteId").toString());
        Paciente paciente = pacienteService.buscarPorId(pacienteId);
        if (paciente == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Paciente no encontrado"));
        }

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setFecha((String) datos.get("fecha"));
        cita.setHora((String) datos.get("hora"));
        cita.setMotivoConsulta((String) datos.get("motivoConsulta"));
        cita.setVeterinarioAsignado((String) datos.get("veterinarioAsignado"));
        cita.setUsuario(principal.getName());

        citaService.guardarCita(cita);
        return ResponseEntity.status(201).body(cita);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Cita cita = citaService.buscarPorId(id);
        if (cita == null) {
            return ResponseEntity.notFound().build();
        }
        citaService.eliminarCita(id);
        return ResponseEntity.noContent().build();
    }
}

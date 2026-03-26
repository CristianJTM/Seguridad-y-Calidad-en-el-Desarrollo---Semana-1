package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.dto.RegistroMedicoDetailDto;
import com.duoc.veterinaria.dto.RegistroMedicoRequest;
import com.duoc.veterinaria.dto.RegistroMedicoSummaryDto;
import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.model.registro.Diagnostico;
import com.duoc.veterinaria.model.registro.RegistroMedico;
import com.duoc.veterinaria.model.registro.Tratamiento;
import com.duoc.veterinaria.service.PacienteService;
import com.duoc.veterinaria.service.RegistroMedicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/registros-medicos")
public class RegistroMedicoApiController {

    private final RegistroMedicoService registroMedicoService;
    private final PacienteService pacienteService;

    public RegistroMedicoApiController(RegistroMedicoService registroMedicoService,
                                       PacienteService pacienteService) {
        this.registroMedicoService = registroMedicoService;
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<RegistroMedicoSummaryDto>> listar(@RequestParam(required = false) Long pacienteId) {
        List<RegistroMedico> registros;

        if (pacienteId != null) {
            Paciente paciente = pacienteService.buscarPorId(pacienteId);
            if (paciente == null) {
                return ResponseEntity.badRequest().build();
            }
            registros = registroMedicoService.obtenerPorPaciente(paciente);
        } else {
            registros = registroMedicoService.obtenerTodos();
        }

        List<RegistroMedicoSummaryDto> dto = registros.stream()
                .map(RegistroMedicoSummaryDto::from)
                .toList();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroMedicoDetailDto> obtener(@PathVariable Long id) {
        return registroMedicoService.obtenerRegistro(id)
                .map(registro -> ResponseEntity.ok(RegistroMedicoDetailDto.from(registro)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RegistroMedicoDetailDto> crear(@RequestBody RegistroMedicoRequest request) {
        if (request.pacienteId() == null || request.veterinario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Paciente paciente = pacienteService.buscarPorId(request.pacienteId());
        if (paciente == null) {
            return ResponseEntity.badRequest().build();
        }

        RegistroMedico registro = new RegistroMedico(paciente, request.veterinario());
        registro.setMotivoConsulta(request.motivoConsulta());
        registro.setSintomas(request.sintomas());
        registro.setObservaciones(request.observaciones());

        if (request.fecha() != null && !request.fecha().isBlank()) {
            try {
                LocalDate fecha = LocalDate.parse(request.fecha());
                registro.setFechaRegistro(fecha.atStartOfDay());
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        if (request.diagnostico() != null && !request.diagnostico().isBlank()) {
            Diagnostico diagnostico = new Diagnostico(
                    request.diagnostico(),
                    LocalDateTime.now(),
                    request.veterinario()
            );
            registro.agregarDiagnostico(diagnostico);
        }

        if (request.tratamiento() != null && !request.tratamiento().isBlank()) {
            Tratamiento tratamiento = new Tratamiento(
                    request.tratamiento(),
                    request.tratamiento(),
                    LocalDate.now(),
                    null,
                    ""
            );
            registro.agregarTratamiento(tratamiento);
        }

        RegistroMedico guardado = registroMedicoService.crearRegistro(registro);
        return ResponseEntity.status(HttpStatus.CREATED).body(RegistroMedicoDetailDto.from(guardado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (registroMedicoService.obtenerRegistro(id).isPresent()) {
            registroMedicoService.eliminarRegistro(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

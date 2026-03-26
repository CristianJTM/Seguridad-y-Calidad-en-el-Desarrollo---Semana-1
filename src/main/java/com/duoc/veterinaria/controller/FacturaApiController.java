package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.dto.FacturaDto;
import com.duoc.veterinaria.dto.FacturaRequest;
import com.duoc.veterinaria.model.factura.Factura;
import com.duoc.veterinaria.model.factura.FacturaEntity;
import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.service.FacturaService;
import com.duoc.veterinaria.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaApiController {

    private final FacturaService facturaService;
    private final PacienteService pacienteService;

    public FacturaApiController(FacturaService facturaService, PacienteService pacienteService) {
        this.facturaService = facturaService;
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<FacturaDto>> listar() {
        List<FacturaEntity> facturas = facturaService.obtenerTodas();
        List<FacturaDto> dto = facturas.stream()
                .map(FacturaDto::from)
                .toList();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDto> obtener(@PathVariable Long id) {
        return facturaService.obtenerFactura(id)
                .map(factura -> ResponseEntity.ok(FacturaDto.from(factura)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FacturaDto> crear(@RequestBody FacturaRequest request) {
        if (request.pacienteId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Paciente paciente = pacienteService.buscarPorId(request.pacienteId());
        if (paciente == null) {
            return ResponseEntity.badRequest().build();
        }

        LocalDate fechaEmision = null;
        if (request.fechaEmision() != null && !request.fechaEmision().isBlank()) {
            try {
                fechaEmision = LocalDate.parse(request.fechaEmision());
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Factura factura = facturaService.crearFacturaBase(request.montoTotal());
        String estado = request.estado();
        if (estado == null || estado.isBlank()) {
            estado = "Pendiente";
        }
        FacturaEntity entity = facturaService.crearYGuardarFactura(paciente, factura, usuario, null);
        entity.setDescripcion(request.descripcionCompleta());
        entity.setNumeroFactura(request.numeroFactura());
        entity.setFechaEmision(fechaEmision);
        entity.setEstado(estado);
        facturaService.guardarFactura(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(FacturaDto.from(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (facturaService.obtenerFactura(id).isPresent()) {
            facturaService.eliminarFactura(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

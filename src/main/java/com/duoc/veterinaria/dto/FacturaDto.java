package com.duoc.veterinaria.dto;

import com.duoc.veterinaria.model.factura.FacturaEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public record FacturaDto(Long id,
                         PacienteInfoDto paciente,
                         String descripcionCompleta,
                         double montoTotal,
                         String estado,
                         String numeroFactura,
                         String fechaEmision,
                         String veterinarioResponsable) {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static FacturaDto from(FacturaEntity factura) {
        return new FacturaDto(
                factura.getId(),
                PacienteInfoDto.from(factura.getPaciente()),
                factura.getDescripcion(),
                factura.getTotal(),
                factura.getEstado(),
                factura.getNumeroFactura(),
                formatFecha(factura.getFechaEmision()),
                factura.getVeterinarioResponsable()
        );
    }

    private static String formatFecha(LocalDate fecha) {
        return Optional.ofNullable(fecha)
                .map(d -> d.format(DATE_FORMATTER))
                .orElse(null);
    }
}

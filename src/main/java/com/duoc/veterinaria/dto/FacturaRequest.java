package com.duoc.veterinaria.dto;

public record FacturaRequest(
        Long pacienteId,
        String numeroFactura,
        String fechaEmision,
        String descripcionCompleta,
        double montoTotal,
        String estado) {
}

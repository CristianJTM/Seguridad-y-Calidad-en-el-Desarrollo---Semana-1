package com.duoc.veterinaria.dto;

public record RegistroMedicoRequest(
        Long pacienteId,
        String fecha,
        String veterinario,
        String motivoConsulta,
        String sintomas,
        String diagnostico,
        String tratamiento,
        String observaciones) {
}

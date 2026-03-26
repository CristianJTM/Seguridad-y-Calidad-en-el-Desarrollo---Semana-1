package com.duoc.veterinaria.dto;

import com.duoc.veterinaria.model.registro.RegistroMedico;
import com.duoc.veterinaria.model.registro.Tratamiento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public record RegistroMedicoDetailDto(Long id,
                                       PacienteInfoDto paciente,
                                       String fecha,
                                       String veterinario,
                                       String motivoConsulta,
                                       String sintomas,
                                       String diagnostico,
                                       String tratamiento,
                                       String observaciones) {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static RegistroMedicoDetailDto from(RegistroMedico registro) {
        return new RegistroMedicoDetailDto(
                registro.getId(),
                PacienteInfoDto.from(registro.getPaciente()),
                formatFecha(registro.getFechaRegistro()),
                registro.getVeterinarioResponsable(),
                registro.getMotivoConsulta(),
                registro.getSintomas(),
                extractDiagnostico(registro),
                extractTratamiento(registro),
                registro.getObservaciones()
        );
    }

    private static String formatFecha(LocalDateTime fecha) {
        return Optional.ofNullable(fecha)
                .map(d -> d.format(DATE_FORMATTER))
                .orElse(null);
    }

    private static String extractDiagnostico(RegistroMedico registro) {
        return registro.getDiagnosticos().stream()
                .map(d -> d.getDescripcion())
                .findFirst()
                .orElse(null);
    }

    private static String extractTratamiento(RegistroMedico registro) {
        return registro.getTratamientos().stream()
                .map(Tratamiento::getDescripcion)
                .filter(desc -> desc != null && !desc.isBlank())
                .findFirst()
                .orElse(null);
    }
}

package com.duoc.veterinaria.dto;

import com.duoc.veterinaria.model.registro.Diagnostico;
import com.duoc.veterinaria.model.registro.RegistroMedico;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public record RegistroMedicoSummaryDto(Long id,
                                        PacienteInfoDto paciente,
                                        String fecha,
                                        String diagnostico,
                                        String veterinario) {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static RegistroMedicoSummaryDto from(RegistroMedico registro) {
        return new RegistroMedicoSummaryDto(
                registro.getId(),
                PacienteInfoDto.from(registro.getPaciente()),
                formatFecha(registro.getFechaRegistro()),
                extractDiagnostico(registro),
                registro.getVeterinarioResponsable()
        );
    }

    private static String formatFecha(LocalDateTime fecha) {
        return Optional.ofNullable(fecha)
                .map(d -> d.format(DATE_FORMATTER))
                .orElse(null);
    }

    private static String extractDiagnostico(RegistroMedico registro) {
        return registro.getDiagnosticos().stream()
                .map(Diagnostico::getDescripcion)
                .findFirst()
                .orElse(null);
    }
}

package com.duoc.veterinaria.dto;

import com.duoc.veterinaria.model.paciente.Paciente;

public record PacienteInfoDto(Long id, String nombre, String especie, String dueno) {

    public static PacienteInfoDto from(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        return new PacienteInfoDto(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getEspecie(),
                paciente.getDueno()
        );
    }
}

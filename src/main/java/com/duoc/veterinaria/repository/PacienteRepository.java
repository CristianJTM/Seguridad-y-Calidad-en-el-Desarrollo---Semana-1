package com.duoc.veterinaria.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.duoc.veterinaria.model.Paciente;

@Repository
public class PacienteRepository {
    private List<Paciente> pacientes = new ArrayList<>();
    private Long contadorId = 1L;


    public List<Paciente> obtenerPacientes() {
        return pacientes;
    }

    public void guardarPaciente(Paciente paciente) {
        paciente.setId(contadorId++);
        pacientes.add(paciente);
    }

    public Paciente buscarPorId(Long id) {
        for (Paciente p : pacientes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}

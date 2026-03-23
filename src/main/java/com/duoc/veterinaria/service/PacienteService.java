package com.duoc.veterinaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.repository.PacienteRepository;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> obtenerPacientes() {
        return pacienteRepository.findAll();
    }

    public void guardarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    public void eliminarPaciente(Long id) {
        pacienteRepository.deleteById(id);
    }
}

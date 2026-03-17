package com.duoc.veterinaria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.model.registro.Diagnostico;
import com.duoc.veterinaria.model.registro.Medicamento;
import com.duoc.veterinaria.model.registro.NotaMedica;
import com.duoc.veterinaria.model.registro.RegistroMedico;
import com.duoc.veterinaria.model.registro.Tratamiento;
import com.duoc.veterinaria.repository.RegistroMedicoRepository;

@Service
public class RegistroMedicoService {
    
    @Autowired
    private RegistroMedicoRepository registroMedicoRepository;
    
    public RegistroMedico crearRegistro(RegistroMedico registroMedico) {
        return registroMedicoRepository.save(registroMedico);
    }
    
    public Optional<RegistroMedico> obtenerRegistro(Long id) {
        return registroMedicoRepository.findById(id);
    }
    
    public List<RegistroMedico> obtenerTodos() {
        return registroMedicoRepository.findAll();
    }
    
    public List<RegistroMedico> obtenerPorPaciente(Paciente paciente) {
        return registroMedicoRepository.findByPaciente(paciente);
    }
    
    public List<RegistroMedico> obtenerPorVeterinario(String veterinario) {
        return registroMedicoRepository.findByVeterinarioResponsable(veterinario);
    }
    
    public RegistroMedico actualizarRegistro(RegistroMedico registroMedico) {
        return registroMedicoRepository.save(registroMedico);
    }
    
    public void eliminarRegistro(Long id) {
        registroMedicoRepository.deleteById(id);
    }
    
    public void agregarDiagnostico(Long registroId, Diagnostico diagnostico) {
        Optional<RegistroMedico> registro = registroMedicoRepository.findById(registroId);
        if (registro.isPresent()) {
            registro.get().agregarDiagnostico(diagnostico);
            registroMedicoRepository.save(registro.get());
        }
    }
    
    public void agregarTratamiento(Long registroId, Tratamiento tratamiento) {
        Optional<RegistroMedico> registro = registroMedicoRepository.findById(registroId);
        if (registro.isPresent()) {
            registro.get().agregarTratamiento(tratamiento);
            registroMedicoRepository.save(registro.get());
        }
    }
    
    public void agregarMedicamento(Long registroId, Medicamento medicamento) {
        Optional<RegistroMedico> registro = registroMedicoRepository.findById(registroId);
        if (registro.isPresent()) {
            registro.get().agregarMedicamento(medicamento);
            registroMedicoRepository.save(registro.get());
        }
    }
    
    public void agregarNota(Long registroId, NotaMedica nota) {
        Optional<RegistroMedico> registro = registroMedicoRepository.findById(registroId);
        if (registro.isPresent()) {
            registro.get().agregarNota(nota);
            registroMedicoRepository.save(registro.get());
        }
    }
}

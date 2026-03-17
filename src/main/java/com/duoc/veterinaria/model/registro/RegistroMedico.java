package com.duoc.veterinaria.model.registro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.duoc.veterinaria.model.paciente.Paciente;

public class RegistroMedico {

    private Long id;

    private Paciente paciente;

    private List<Diagnostico> diagnosticos = new ArrayList<>();

    private List<Tratamiento> tratamientos = new ArrayList<>();

    private List<Medicamento> medicamentos = new ArrayList<>();

    private List<NotaMedica> notas = new ArrayList<>();
    
    private LocalDateTime fechaRegistro;
    
    private String veterinarioResponsable;

    public RegistroMedico() {
    }

    public RegistroMedico(Paciente paciente, String veterinarioResponsable) {
        this.paciente = paciente;
        this.veterinarioResponsable = veterinarioResponsable;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Diagnostico> getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(List<Diagnostico> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public void agregarDiagnostico(Diagnostico diagnostico) {
        this.diagnosticos.add(diagnostico);
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public void agregarTratamiento(Tratamiento tratamiento) {
        this.tratamientos.add(tratamiento);
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public void agregarMedicamento(Medicamento medicamento) {
        this.medicamentos.add(medicamento);
    }

    public List<NotaMedica> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaMedica> notas) {
        this.notas = notas;
    }

    public void agregarNota(NotaMedica nota) {
        this.notas.add(nota);
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getVeterinarioResponsable() {
        return veterinarioResponsable;
    }

    public void setVeterinarioResponsable(String veterinarioResponsable) {
        this.veterinarioResponsable = veterinarioResponsable;
    }
}
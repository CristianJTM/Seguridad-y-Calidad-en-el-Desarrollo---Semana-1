package com.duoc.veterinaria.model.registro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.duoc.veterinaria.model.paciente.Paciente;
import jakarta.persistence.*;

@Entity
public class RegistroMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @OneToMany(mappedBy = "registroMedico", cascade = CascadeType.ALL)
    private List<Diagnostico> diagnosticos = new ArrayList<>();

    @OneToMany(mappedBy = "registroMedico", cascade = CascadeType.ALL)
    private List<Tratamiento> tratamientos = new ArrayList<>();

    @OneToMany(mappedBy = "registroMedico", cascade = CascadeType.ALL)
    private List<Medicamento> medicamentos = new ArrayList<>();

    @OneToMany(mappedBy = "registroMedico", cascade = CascadeType.ALL)
    private List<NotaMedica> notas = new ArrayList<>();
    
    private LocalDateTime fechaRegistro;
    
    private String veterinarioResponsable;

    @Column(length = 1000)
    private String motivoConsulta;

    @Column(length = 1000)
    private String sintomas;

    @Column(length = 1000)
    private String observaciones;

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
        diagnostico.setRegistroMedico(this);
        this.diagnosticos.add(diagnostico);
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public void agregarTratamiento(Tratamiento tratamiento) {
        tratamiento.setRegistroMedico(this);
        this.tratamientos.add(tratamiento);
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public void agregarMedicamento(Medicamento medicamento) {
        medicamento.setRegistroMedico(this);
        this.medicamentos.add(medicamento);
    }

    public List<NotaMedica> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaMedica> notas) {
        this.notas = notas;
    }

    public void agregarNota(NotaMedica nota) {
        nota.setRegistroMedico(this);
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

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
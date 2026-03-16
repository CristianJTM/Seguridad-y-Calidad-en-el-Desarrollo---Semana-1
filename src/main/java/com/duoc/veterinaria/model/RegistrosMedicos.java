package com.duoc.veterinaria.model;

public class RegistrosMedicos {
    private Long id;
    private Cita cita;
    private String diagnostico;
    private String tratamiento;
    private String medicamentos;

    public RegistrosMedicos() {
    }

    public RegistrosMedicos(Long id,Cita cita, String diagnostico, String tratamiento, String medicamentos) {
        this.id = id;
        this.cita = cita;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.medicamentos = medicamentos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

}

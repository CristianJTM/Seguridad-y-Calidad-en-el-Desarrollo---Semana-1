package com.duoc.veterinaria.model;

public class Cita {
    //El sistema debe permitir programar citas para los pacientes, incluyendo la fecha, hora, motivo de la consulta y veterinario asignado. Esta información se debe almacenar en memoria, siguiendo un esquema similar a los usuarios.
    private Paciente paciente;
    private String fecha;
    private String hora;
    private String motivoConsulta;
    private String veterinarioAsignado;
    private String usuario;

    public Cita() {
    }

    public Cita(Paciente paciente, String fecha, String hora, String motivoConsulta, String veterinarioAsignado, String usuario) {
        this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
        this.motivoConsulta = motivoConsulta;
        this.veterinarioAsignado = veterinarioAsignado;
        this.usuario = usuario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getVeterinarioAsignado() {
        return veterinarioAsignado;
    }

    public void setVeterinarioAsignado(String veterinarioAsignado) {
        this.veterinarioAsignado = veterinarioAsignado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}

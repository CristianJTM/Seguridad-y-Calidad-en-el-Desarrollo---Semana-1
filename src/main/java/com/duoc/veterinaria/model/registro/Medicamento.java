package com.duoc.veterinaria.model.registro;

public class Medicamento {

    private Long id;
    
    private String nombre;
    
    private String dosis;
    
    private String frecuencia;
    
    private String viaAdministracion;
    
    private int duracionDias;

    public Medicamento() {
    }

    public Medicamento(String nombre, String dosis, String frecuencia, String viaAdministracion, int duracionDias) {
        this.nombre = nombre;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.viaAdministracion = viaAdministracion;
        this.duracionDias = duracionDias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }
}

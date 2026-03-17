package com.duoc.veterinaria.model.factura.cargos;

import com.duoc.veterinaria.model.factura.Factura;

public class ServicioCargo extends CargoAdicional {

    private double costo;
    
    private String tipoServicio;

    public ServicioCargo(Factura factura, double costo, String tipoServicio) {
        super(factura);
        this.costo = costo;
        this.tipoServicio = tipoServicio;
    }

    @Override
    public String getDescripcion() {
        return factura.getDescripcion() + " + Servicio: " + tipoServicio;
    }

    @Override
    public double getCosto() {
        return factura.getCosto() + costo;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public double getCostoServicio() {
        return costo;
    }

    public void setCostoServicio(double costo) {
        this.costo = costo;
    }
}

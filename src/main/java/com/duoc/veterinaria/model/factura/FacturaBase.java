package com.duoc.veterinaria.model.factura;

public class FacturaBase implements Factura {

    private double costoBase;

    public FacturaBase(double costoBase) {
        this.costoBase = costoBase;
    }

    public String getDescripcion() {
        return "Consulta veterinaria";
    }

    public double getCosto() {
        return costoBase;
    }
}
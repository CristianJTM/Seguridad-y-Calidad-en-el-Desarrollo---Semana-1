package com.duoc.veterinaria.model.factura.cargos;

import com.duoc.veterinaria.model.factura.Factura;

public class TratamientoCargo extends CargoAdicional {

    private double costo;

    public TratamientoCargo(Factura factura, double costo) {
        super(factura);
        this.costo = costo;
    }

    public String getDescripcion() {
        return factura.getDescripcion() + " + Tratamiento";
    }

    public double getCosto() {
        return factura.getCosto() + costo;
    }
}
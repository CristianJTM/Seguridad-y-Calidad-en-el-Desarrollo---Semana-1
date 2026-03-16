package com.duoc.veterinaria.model.factura.cargos;
import com.duoc.veterinaria.model.factura.Factura;

public class MedicamentoCargo extends CargoAdicional {

    private double costo;

    public MedicamentoCargo(Factura factura, double costo) {
        super(factura);
        this.costo = costo;
    }

    public String getDescripcion() {
        return factura.getDescripcion() + " + Medicamento";
    }

    public double getCosto() {
        return factura.getCosto() + costo;
    }
}
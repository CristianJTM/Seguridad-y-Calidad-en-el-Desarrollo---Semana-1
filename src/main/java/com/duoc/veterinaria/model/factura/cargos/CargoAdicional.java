package com.duoc.veterinaria.model.factura.cargos;
import com.duoc.veterinaria.model.factura.Factura;
import com.duoc.veterinaria.model.factura.FacturaDecorator;

public abstract class CargoAdicional extends FacturaDecorator {

    public CargoAdicional(Factura factura) {
        super(factura);
    }
}
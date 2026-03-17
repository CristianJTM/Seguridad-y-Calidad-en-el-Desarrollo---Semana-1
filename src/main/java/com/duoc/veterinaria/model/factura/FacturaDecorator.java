package com.duoc.veterinaria.model.factura;

public abstract class FacturaDecorator implements Factura {

    protected Factura factura;

    public FacturaDecorator(Factura factura) {
        this.factura = factura;
    }
}
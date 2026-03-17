package com.duoc.veterinaria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.veterinaria.model.factura.Factura;
import com.duoc.veterinaria.model.factura.FacturaBase;
import com.duoc.veterinaria.model.factura.FacturaEntity;
import com.duoc.veterinaria.model.factura.cargos.MedicamentoCargo;
import com.duoc.veterinaria.model.factura.cargos.ServicioCargo;
import com.duoc.veterinaria.model.factura.cargos.TratamientoCargo;
import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.repository.FacturaRepository;

@Service
public class FacturaService {
    
    @Autowired
    private FacturaRepository facturaRepository;
    
    public Factura crearFacturaBase(double costoBase) {
        return new FacturaBase(costoBase);
    }
    
    public Factura agregarCostoMedicamento(Factura factura, double costo) {
        return new MedicamentoCargo(factura, costo);
    }
    
    public Factura agregarTratamiento(Factura factura, double costo) {
        return new TratamientoCargo(factura, costo);
    }
    
    public Factura agregarServicio(Factura factura, double costo, String tipoServicio) {
        return new ServicioCargo(factura, costo, tipoServicio);
    }
    
    public FacturaEntity guardarFactura(FacturaEntity facturaEntity) {
        return facturaRepository.save(facturaEntity);
    }
    
    public Optional<FacturaEntity> obtenerFactura(Long id) {
        return facturaRepository.findById(id);
    }
    
    public List<FacturaEntity> obtenerTodas() {
        return facturaRepository.findAll();
    }
    
    public List<FacturaEntity> obtenerPorPaciente(Paciente paciente) {
        return facturaRepository.findByPaciente(paciente);
    }
    
    public void eliminarFactura(Long id) {
        facturaRepository.deleteById(id);
    }
    
    public double calcularTotal(Factura factura) {
        return factura.getCosto();
    }
    
    public String obtenerDescripcionCompleta(Factura factura) {
        return factura.getDescripcion();
    }
}

package com.duoc.veterinaria.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.duoc.veterinaria.model.factura.FacturaEntity;
import com.duoc.veterinaria.model.paciente.Paciente;

@Repository
public class FacturaRepository {
    private List<FacturaEntity> facturas = new ArrayList<>();
    private Long contadorId = 1L;
    
    public FacturaEntity save(FacturaEntity facturaEntity) {
        if (facturaEntity.getId() == null) {
            facturaEntity.setId(contadorId++);
        }
        facturas.remove(facturaEntity);
        facturas.add(facturaEntity);
        return facturaEntity;
    }
    
    public Optional<FacturaEntity> findById(Long id) {
        return facturas.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst();
    }
    
    public List<FacturaEntity> findAll() {
        return new ArrayList<>(facturas);
    }
    
    public List<FacturaEntity> findByPaciente(Paciente paciente) {
        List<FacturaEntity> resultado = new ArrayList<>();
        for (FacturaEntity f : facturas) {
            if (f.getPaciente() != null && f.getPaciente().getId().equals(paciente.getId())) {
                resultado.add(f);
            }
        }
        return resultado;
    }
    
    public void deleteById(Long id) {
        facturas.removeIf(f -> f.getId().equals(id));
    }
}

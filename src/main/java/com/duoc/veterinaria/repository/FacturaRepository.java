package com.duoc.veterinaria.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.veterinaria.model.factura.FacturaEntity;
import com.duoc.veterinaria.model.paciente.Paciente;

@Repository
public interface FacturaRepository extends JpaRepository<FacturaEntity, Long> {

    List<FacturaEntity> findByPaciente(Paciente paciente);

    List<FacturaEntity> findByVeterinarioResponsable(String veterinarioResponsable);
}

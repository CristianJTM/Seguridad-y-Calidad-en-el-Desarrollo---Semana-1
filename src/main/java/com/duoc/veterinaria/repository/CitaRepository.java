package com.duoc.veterinaria.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.duoc.veterinaria.model.Cita;

@Repository
public class CitaRepository {
    private List<Cita> citas = new ArrayList<>();

    public List<Cita> obtenerCitas() {
        return citas;
    }

    public void guardarCita(Cita cita) {
        citas.add(cita);
    }
}

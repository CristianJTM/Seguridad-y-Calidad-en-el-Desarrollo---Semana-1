package com.duoc.veterinaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.veterinaria.model.Cita;
import com.duoc.veterinaria.repository.CitaRepository;

@Service
public class CitaService {
    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> obtenerCitas() {
        return citaRepository.obtenerCitas();
    }

    public void guardarCita(Cita cita) {
        citaRepository.guardarCita(cita);
    }

    public Cita buscarPorId(Long id) {
        List<Cita> citas = citaRepository.obtenerCitas();
        for (Cita c : citas) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void eliminarCita(Long id) {
        List<Cita> citas = citaRepository.obtenerCitas();
        citas.removeIf(c -> c.getId().equals(id));
    }
}

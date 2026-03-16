package com.duoc.veterinaria.repository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.duoc.veterinaria.model.RegistrosMedicos;

@Repository
public class RegistroMedicoRepository {

    private List<RegistrosMedicos> registrosMedicos = new ArrayList<>();
    private Long contadorId = 1L;

    public List<RegistrosMedicos> obtenerRegistrosMedicos() {
        return registrosMedicos;
    }

    public void guardarRegistroMedico(RegistrosMedicos registroMedico) {
        registroMedico.setId(contadorId++);
        registrosMedicos.add(registroMedico);
    }
}

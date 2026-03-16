package com.duoc.veterinaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.veterinaria.model.RegistrosMedicos;
import com.duoc.veterinaria.repository.RegistroMedicoRepository;

@Service
public class RegistroMedicoService {

    @Autowired
    private RegistroMedicoRepository registroMedicoRepository;

    public List<RegistrosMedicos> obtenerRegistrosMedicos() {
        return registroMedicoRepository.obtenerRegistrosMedicos();
    }

    public void guardarRegistroMedico(RegistrosMedicos registroMedico) {
        registroMedicoRepository.guardarRegistroMedico(registroMedico);
    }
}

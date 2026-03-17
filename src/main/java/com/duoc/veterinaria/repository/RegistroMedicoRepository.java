package com.duoc.veterinaria.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.model.registro.RegistroMedico;

@Repository
public class RegistroMedicoRepository {
    private List<RegistroMedico> registros = new ArrayList<>();
    private Long contadorId = 1L;
    
    public RegistroMedico save(RegistroMedico registroMedico) {
        if (registroMedico.getId() == null) {
            registroMedico.setId(contadorId++);
        }
        registros.remove(registroMedico);
        registros.add(registroMedico);
        return registroMedico;
    }
    
    public Optional<RegistroMedico> findById(Long id) {
        return registros.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }
    
    public List<RegistroMedico> findAll() {
        return new ArrayList<>(registros);
    }
    
    public List<RegistroMedico> findByPaciente(Paciente paciente) {
        List<RegistroMedico> resultado = new ArrayList<>();
        for (RegistroMedico r : registros) {
            if (r.getPaciente().getId().equals(paciente.getId())) {
                resultado.add(r);
            }
        }
        return resultado;
    }
    
    public List<RegistroMedico> findByVeterinarioResponsable(String veterinarioResponsable) {
        List<RegistroMedico> resultado = new ArrayList<>();
        for (RegistroMedico r : registros) {
            if (r.getVeterinarioResponsable().equals(veterinarioResponsable)) {
                resultado.add(r);
            }
        }
        return resultado;
    }
    
    public void deleteById(Long id) {
        registros.removeIf(r -> r.getId().equals(id));
    }
}

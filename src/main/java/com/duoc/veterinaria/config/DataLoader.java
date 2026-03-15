package com.duoc.veterinaria.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.duoc.veterinaria.model.Paciente;
import com.duoc.veterinaria.service.PacienteService;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner cargarDatos(PacienteService pacienteService) {
        return args -> {

            pacienteService.guardarPaciente(
                    new Paciente(null, "Firulais", "Perro", "Labrador", 5, "Juan Perez")
            );

            pacienteService.guardarPaciente(
                    new Paciente(null, "Michi", "Gato", "Siames", 3, "Maria Lopez")
            );

            pacienteService.guardarPaciente(
                    new Paciente(null, "Rocky", "Perro", "Pastor Alemán", 4, "Pedro Soto")
            );

        };
    }
}

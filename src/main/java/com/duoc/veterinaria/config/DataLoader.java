package com.duoc.veterinaria.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.service.PacienteService;
import com.duoc.veterinaria.model.Cita;
import com.duoc.veterinaria.service.CitaService;

/*
 * Cargar datos iniciales en la base de datos.
 * Se utiliza CommandLineRunner para ejecutar el código después de que la aplicación se haya iniciado.
 */

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner cargarDatosPacientes(PacienteService pacienteService , CitaService citaService) {
        return args -> {

            Paciente p1 = new Paciente(null, "Firulais", "Perro", "Labrador", 5, "Juan Perez");
            Paciente p2 = new Paciente(null, "Michi", "Gato", "Siames", 3, "Maria Lopez");
            Paciente p3 = new Paciente(null, "Rocky", "Perro", "Pastor Alemán", 4, "Pedro Soto");

            pacienteService.guardarPaciente(p1);
            pacienteService.guardarPaciente(p2);
            pacienteService.guardarPaciente(p3);

            citaService.guardarCita(
                    new Cita(p1, null,"2026-03-20", "10:00", "Vacunación", "Veterinario 1", "admin")
            );

            citaService.guardarCita(
                    new Cita(p2, null,"2026-03-21", "11:30", "Control general", "Veterinario 2", "admin")
            );

            citaService.guardarCita(
                    new Cita(p3, null,"2026-03-22", "15:00", "Dolor en pata", "Veterinario 3", "admin")
            );

        };
    }

}

package com.duoc.veterinaria.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.duoc.veterinaria.model.cita.Cita;
import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.service.CitaService;
import com.duoc.veterinaria.service.PacienteService;


@Controller
public class CitaController {
    @Autowired
    private CitaService citaService;
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/cita")
    public String listarCitas(Model model) {
        model.addAttribute("citas", citaService.obtenerCitas());
        model.addAttribute("pacientes", pacienteService.obtenerPacientes());
        return "cita/index";
    }

    @GetMapping("/cita/nuevo")
    public String mostrarFormularioNuevaCita(Model model) {
        model.addAttribute("cita", new Cita());
        return "cita/nuevo";
    }

    @PostMapping("/cita")
    public String guardarCita(Long pacienteId, String fecha, String hora, String motivoConsulta, String veterinarioAsignado, Principal principal) {
        Paciente paciente = pacienteService.buscarPorId(pacienteId);

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setMotivoConsulta(motivoConsulta);
        cita.setVeterinarioAsignado(veterinarioAsignado);
        cita.setUsuario(principal.getName());

        citaService.guardarCita(cita);

        return "redirect:/cita";
    }
}

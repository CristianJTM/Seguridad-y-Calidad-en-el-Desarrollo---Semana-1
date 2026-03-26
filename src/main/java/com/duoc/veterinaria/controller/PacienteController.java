package com.duoc.veterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.service.PacienteService;

@Controller
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/paciente")
    public String listarPacientes(Model model) {
        model.addAttribute("pacientes", pacienteService.obtenerPacientes());
        return "paciente/index";
    }

    @GetMapping("/paciente/nuevo")
    public String mostrarFormularioNuevoPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "paciente/nuevo";
    }

    @GetMapping("/paciente/detalle")
    public String mostrarDetallePaciente() {
        return "paciente/detalle";
    }

    @PostMapping("/paciente")
    public String guardarPaciente(Paciente paciente) {
        pacienteService.guardarPaciente(paciente);
        return "redirect:/paciente";
    }
}

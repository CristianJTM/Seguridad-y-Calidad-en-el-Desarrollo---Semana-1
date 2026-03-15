package com.duoc.veterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.duoc.veterinaria.model.Paciente;
import com.duoc.veterinaria.service.PacienteService;

@Controller
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/pacientes")
    public String listarPacientes(Model model) {
        model.addAttribute("pacientes", pacienteService.obtenerPacientes());
        return "pacientes";
    }

    @GetMapping("/pacientes/nuevo")
    public String mostrarFormularioNuevoPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "nuevo_paciente";
    }

    @PostMapping("/pacientes")
    public String guardarPaciente(Paciente paciente) {
        pacienteService.guardarPaciente(paciente);
        return "redirect:/pacientes";
    }
}

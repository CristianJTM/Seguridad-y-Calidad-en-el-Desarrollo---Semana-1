package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.duoc.veterinaria.model.RegistrosMedicos;
import com.duoc.veterinaria.service.RegistroMedicoService;
import com.duoc.veterinaria.service.CitaService;
import com.duoc.veterinaria.model.Cita;

@Controller
public class RegistroMedicoController {

    @Autowired
    private RegistroMedicoService registroMedicoService;
    @Autowired
    private CitaService citaService;

    @GetMapping("/registros")
    public String listarRegistrosMedicos(Model model) {
        model.addAttribute("registros", registroMedicoService.obtenerRegistrosMedicos());
        model.addAttribute("citas", citaService.obtenerCitas());
        return "registros";
    }

    @GetMapping("/registros/nuevo")
    public String mostrarFormularioNuevoRegistroMedico(Model model) {
        model.addAttribute("registro", new RegistrosMedicos());
        return "nuevo_registro";
    }

    @PostMapping("/registros")
    public String guardarRegistroMedico(Long citaId,
                                        String diagnostico,
                                        String tratamiento,
                                        String medicamentos) {
        Cita cita = citaService.buscarPorId(citaId);

        RegistrosMedicos registro = new RegistrosMedicos();
        registro.setCita(cita);
        registro.setDiagnostico(diagnostico);
        registro.setTratamiento(tratamiento);
        registro.setMedicamentos(medicamentos);

        registroMedicoService.guardarRegistroMedico(registro);

        citaService.eliminarCita(citaId);
        return "redirect:/registros";
    }

}

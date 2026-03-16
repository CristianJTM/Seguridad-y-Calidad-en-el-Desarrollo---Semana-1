package com.duoc.veterinaria.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.model.registro.Diagnostico;
import com.duoc.veterinaria.model.registro.Medicamento;
import com.duoc.veterinaria.model.registro.NotaMedica;
import com.duoc.veterinaria.model.registro.RegistroMedico;
import com.duoc.veterinaria.model.registro.Tratamiento;
import com.duoc.veterinaria.service.PacienteService;
import com.duoc.veterinaria.service.RegistroMedicoService;

@Controller
@RequestMapping("/registros-medicos")
public class RegistroMedicoController {
    
    @Autowired
    private RegistroMedicoService registroMedicoService;
    
    @Autowired
    private PacienteService pacienteService;
    
    @GetMapping
    public String listarRegistros(Model model) {
        model.addAttribute("registros", registroMedicoService.obtenerTodos());
        model.addAttribute("pacientes", pacienteService.obtenerPacientes());
        return "registros";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo() {
        return "redirect:/registros-medicos";
    }
    
    @PostMapping
    public String crearRegistro(@RequestParam Long pacienteId,
                                @RequestParam String veterinarioResponsable) {
        Paciente paciente = pacienteService.buscarPorId(pacienteId);
        if (paciente != null) {
            RegistroMedico nuevoRegistro = new RegistroMedico(paciente, veterinarioResponsable);
            registroMedicoService.crearRegistro(nuevoRegistro);
        }
        return "redirect:/registros-medicos";
    }
    
    @GetMapping("/{id}")
    public String verDetalles(@PathVariable Long id) {
        return "redirect:/registros-medicos";
    }
    
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id) {
        return "redirect:/registros-medicos";
    }
    
    @PostMapping("/{id}")
    public String actualizarRegistro(@PathVariable Long id,
                                     @RequestParam(required = false) Long pacienteId,
                                     @RequestParam String veterinarioResponsable) {
        registroMedicoService.obtenerRegistro(id).ifPresent(registroExistente -> {
            registroExistente.setVeterinarioResponsable(veterinarioResponsable);
            if (pacienteId != null) {
                Paciente paciente = pacienteService.buscarPorId(pacienteId);
                if (paciente != null) {
                    registroExistente.setPaciente(paciente);
                }
            }
            registroMedicoService.actualizarRegistro(registroExistente);
        });
        return "redirect:/registros-medicos";
    }
    
    @PostMapping("/{id}/diagnostico")
    public String agregarDiagnostico(@PathVariable Long id, 
                                     @RequestParam String descripcion,
                                     @RequestParam String veterinario) {
        Diagnostico diagnostico = new Diagnostico(descripcion, LocalDateTime.now(), veterinario);
        registroMedicoService.agregarDiagnostico(id, diagnostico);
        return "redirect:/registros-medicos";
    }
    
    @PostMapping("/{id}/tratamiento")
    public String agregarTratamiento(@PathVariable Long id,
                                    @RequestParam String nombre,
                                    @RequestParam String descripcion,
                                    @RequestParam String instrucciones) {
        Tratamiento tratamiento = new Tratamiento(nombre, descripcion, null, null, instrucciones);
        registroMedicoService.agregarTratamiento(id, tratamiento);
        return "redirect:/registros-medicos";
    }
    
    @PostMapping("/{id}/medicamento")
    public String agregarMedicamento(@PathVariable Long id,
                                    @RequestParam String nombre,
                                    @RequestParam String dosis,
                                    @RequestParam String frecuencia,
                                    @RequestParam String viaAdministracion,
                                    @RequestParam int duracionDias) {
        Medicamento medicamento = new Medicamento(nombre, dosis, frecuencia, viaAdministracion, duracionDias);
        registroMedicoService.agregarMedicamento(id, medicamento);
        return "redirect:/registros-medicos";
    }
    
    @PostMapping("/{id}/nota")
    public String agregarNota(@PathVariable Long id,
                             @RequestParam String contenido,
                             @RequestParam String autor) {
        NotaMedica nota = new NotaMedica(contenido, LocalDateTime.now(), autor);
        registroMedicoService.agregarNota(id, nota);
        return "redirect:/registros-medicos";
    }
    
    @PostMapping("/{id}/eliminar")
    public String eliminarRegistro(@PathVariable Long id) {
        registroMedicoService.eliminarRegistro(id);
        return "redirect:/registros-medicos";
    }
}

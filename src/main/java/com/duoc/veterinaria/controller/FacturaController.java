package com.duoc.veterinaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.duoc.veterinaria.model.factura.Factura;
import com.duoc.veterinaria.model.factura.FacturaEntity;
import com.duoc.veterinaria.model.paciente.Paciente;
import com.duoc.veterinaria.service.FacturaService;
import com.duoc.veterinaria.service.PacienteService;

@Controller
@RequestMapping("/facturas")
public class FacturaController {
    
    @Autowired
    private FacturaService facturaService;

    @Autowired
    private PacienteService pacienteService;

    private List<String> obtenerVeterinariosDisponibles() {
        return List.of("Veterinario 1", "Veterinario 2", "Veterinario 3");
    }
    
    @GetMapping
    public String listarFacturas(Model model) {
        model.addAttribute("facturas", facturaService.obtenerTodas());
        model.addAttribute("pacientes", pacienteService.obtenerPacientes());
        model.addAttribute("veterinarios", obtenerVeterinariosDisponibles());
        return "facturas";
    }
    
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        return "redirect:/facturas";
    }
    
    @PostMapping
    public String crearFactura(@RequestParam Long pacienteId,
                               @RequestParam String veterinarioResponsable,
                               @RequestParam(required = false) String notas,
                               @RequestParam double costoConsulta,
                               @RequestParam(defaultValue = "0") double costoMedicamento,
                               @RequestParam(defaultValue = "0") double costoInsumos,
                               @RequestParam(defaultValue = "0") double costoServicio,
                               @RequestParam(required = false) String tipoServicio) {
        Paciente paciente = pacienteService.buscarPorId(pacienteId);
        Factura facturaDeco = facturaService.crearFacturaBase(costoConsulta);

        if (costoMedicamento > 0) {
            facturaDeco = facturaService.agregarCostoMedicamento(facturaDeco, costoMedicamento);
        }
        if (costoInsumos > 0) {
            facturaDeco = facturaService.agregarServicio(facturaDeco, costoInsumos, "Insumos");
        }
        if (costoServicio > 0) {
            String servicio = (tipoServicio == null || tipoServicio.isBlank()) ? "Servicio Adicional" : tipoServicio;
            facturaDeco = facturaService.agregarServicio(facturaDeco, costoServicio, servicio);
        }

        facturaService.crearYGuardarFactura(
                paciente,
                facturaDeco,
                veterinarioResponsable,
                notas
        );
        return "redirect:/facturas";
    }
    
    @GetMapping("/{id}")
    public String verDetalles(@PathVariable Long id, Model model) {
        return facturaService.obtenerFactura(id)
                .map(factura -> {
                    model.addAttribute("factura", factura);
                    model.addAttribute("pacientes", pacienteService.obtenerPacientes());
                    model.addAttribute("veterinarios", obtenerVeterinariosDisponibles());
                    return "detalle-factura";
                })
                .orElse("redirect:/facturas");
    }

    @PostMapping("/{id}/actualizar")
    public String actualizarFactura(@PathVariable Long id,
                                    @RequestParam Long pacienteId,
                                    @RequestParam String veterinarioResponsable,
                                    @RequestParam(required = false) String notas) {
        facturaService.obtenerFactura(id).ifPresent(facturaEntity -> {
            Paciente paciente = pacienteService.buscarPorId(pacienteId);
            if (paciente != null) {
                facturaEntity.setPaciente(paciente);
            }
            facturaEntity.setVeterinarioResponsable(veterinarioResponsable);
            facturaEntity.setNotas(notas);
            facturaService.guardarFactura(facturaEntity);
        });
        return "redirect:/facturas/" + id;
    }
    
    @PostMapping("/{id}/medicamento")
    public String agregarMedicamento(@PathVariable Long id,
                                    @RequestParam double costo) {
        facturaService.agregarCosto(id, costo, "Medicamento");
        return "redirect:/facturas/" + id;
    }
    
    @PostMapping("/{id}/tratamiento")
    public String agregarTratamiento(@PathVariable Long id,
                                    @RequestParam double costo) {
        facturaService.agregarCosto(id, costo, "Tratamiento");
        return "redirect:/facturas/" + id;
    }

    @PostMapping("/{id}/insumo")
    public String agregarInsumo(@PathVariable Long id,
                                @RequestParam String nombreInsumo,
                                @RequestParam double costo) {
        facturaService.agregarCosto(id, costo, "Insumo: " + nombreInsumo);
        return "redirect:/facturas/" + id;
    }
    
    @PostMapping("/{id}/servicio")
    public String agregarServicio(@PathVariable Long id,
                                 @RequestParam double costo,
                                 @RequestParam String tipoServicio) {
        facturaService.agregarCosto(id, costo, tipoServicio);
        return "redirect:/facturas/" + id;
    }
    
    @PostMapping("/{id}/eliminar")
    public String eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return "redirect:/facturas";
    }
}

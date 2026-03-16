package com.duoc.veterinaria.controller;

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
import com.duoc.veterinaria.service.FacturaService;

@Controller
@RequestMapping("/facturas")
public class FacturaController {
    
    @Autowired
    private FacturaService facturaService;
    
    @GetMapping
    public String listarFacturas(Model model) {
        model.addAttribute("facturas", facturaService.obtenerTodas());
        return "facturas";
    }
    
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        return "redirect:/facturas";
    }
    
    @PostMapping
    public String crearFactura(@RequestParam double costoBase) {
        Factura facturaDeco = facturaService.crearFacturaBase(costoBase);
        FacturaEntity facturaEntity = new FacturaEntity(null, facturaDeco);
        facturaService.guardarFactura(facturaEntity);
        return "redirect:/facturas";
    }
    
    @GetMapping("/{id}")
    public String verDetalles(@PathVariable Long id, Model model) {
        return "redirect:/facturas";
    }
    
    @PostMapping("/{id}/medicamento")
    public String agregarMedicamento(@PathVariable Long id,
                                    @RequestParam double costo) {
        facturaService.obtenerFactura(id).ifPresent(facturaEntity -> {
            Factura facturaActualizada = facturaService.agregarCostoMedicamento(facturaEntity.getFactura(), costo);
            facturaEntity.setFactura(facturaActualizada);
            facturaService.guardarFactura(facturaEntity);
        });
        return "redirect:/facturas";
    }
    
    @PostMapping("/{id}/tratamiento")
    public String agregarTratamiento(@PathVariable Long id,
                                    @RequestParam double costo) {
        facturaService.obtenerFactura(id).ifPresent(facturaEntity -> {
            Factura facturaActualizada = facturaService.agregarTratamiento(facturaEntity.getFactura(), costo);
            facturaEntity.setFactura(facturaActualizada);
            facturaService.guardarFactura(facturaEntity);
        });
        return "redirect:/facturas";
    }
    
    @PostMapping("/{id}/servicio")
    public String agregarServicio(@PathVariable Long id,
                                 @RequestParam double costo,
                                 @RequestParam String tipoServicio) {
        facturaService.obtenerFactura(id).ifPresent(facturaEntity -> {
            Factura facturaActualizada = facturaService.agregarServicio(facturaEntity.getFactura(), costo, tipoServicio);
            facturaEntity.setFactura(facturaActualizada);
            facturaService.guardarFactura(facturaEntity);
        });
        return "redirect:/facturas";
    }
    
    @PostMapping("/{id}/eliminar")
    public String eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return "redirect:/facturas";
    }
}

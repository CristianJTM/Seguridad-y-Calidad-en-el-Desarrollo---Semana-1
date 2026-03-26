package com.duoc.veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller para las páginas principales públicas del sistema.
 */
@Controller
public class HomeController {

    /**
     * Página de inicio del sistema
     * Ruta: /home -> templates/home/index.html
     */
    @GetMapping("/home")
    public String home(@RequestParam(name="name", required=false, defaultValue="Veterinaria") String name, Model model) {
        model.addAttribute("name", name);
        return "home/index";
    }

    /**
     * Redirección raíz a página de inicio
     * Ruta: / -> templates/home/index.html
     */
    @GetMapping("/")
    public String root(@RequestParam(name="name", required=false, defaultValue="Veterinaria") String name, Model model) {
        model.addAttribute("name", name);
        return "home/index";
    }

    /**
     * Página de acceso denegado (cuando el usuario no tiene permisos)
     * Ruta: /acceso-denegado -> templates/acceso-denegado.html
     */
    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }

    /**
     * Panel principal del sistema
     * Ruta: /panel -> templates/home/panel.html
     */
    @GetMapping("/panel")
    public String panel() {
        return "home/panel";
    }
}

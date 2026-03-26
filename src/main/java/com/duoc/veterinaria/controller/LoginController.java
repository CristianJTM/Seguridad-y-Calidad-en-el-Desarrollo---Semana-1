package com.duoc.veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller para la página de login del sistema.
 */
@Controller
public class LoginController {

    /**
     * Página de inicio de sesión con JWT
     * Ruta: /login -> templates/home/login.html
     */
    @GetMapping("/login")
    public String login() {
        return "home/login";
    }
}

package com.duoc.veterinaria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad del sistema veterinaria.
 *
 * Este archivo configura dos cadenas de seguridad separadas:
 * 1. API REST (JWT stateless) - Para endpoints /api/**
 * 2. Vistas web (rutas públicas) - Para páginas HTML
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public WebSecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    // ============================================
    // CADENA 1: SEGURIDAD PARA API REST (JWT)
    // ============================================

    /**
     * Configura la seguridad para las APIs REST con JWT.
     *
     * Características:
     * - Stateless (sin sesiones)
     * - Autenticación mediante JWT en header Authorization
     * - CSRF deshabilitado (no necesario para JWT)
     *
     * Rutas y permisos:
     * - /api/auth/login, /api/auth/register: Públicas (autenticación)
     * - /api/pacientes/**: USER, VET, ADMIN
     * - /api/citas/**: USER, VET, ADMIN
     * - /api/registros-medicos/**: VET, ADMIN
     * - /api/facturas/**: ADMIN
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        // Rutas públicas de autenticación
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()

                        // Rutas protegidas por roles
                        .requestMatchers("/api/pacientes/**").hasAnyRole("USER", "VET", "ADMIN")
                        .requestMatchers("/api/citas/**").hasAnyRole("USER", "VET", "ADMIN")
                        .requestMatchers("/api/registros-medicos/**").hasAnyRole("VET", "ADMIN")
                        .requestMatchers("/api/facturas/**").hasRole("ADMIN")
                        .requestMatchers("/api/veterinarios/**").hasAnyRole("USER", "VET", "ADMIN")

                        // Cualquier otra ruta API requiere autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // ============================================
    // CADENA 2: SEGURIDAD PARA VISTAS WEB
    // ============================================

    /**
     * Configura la seguridad para las vistas Thymeleaf.
     *
     * IMPORTANTE: Las vistas están configuradas como públicas porque
     * la autenticación y autorización se maneja del lado del cliente
     * usando JWT almacenado en localStorage.
     *
     * Estructura de directorios:
     * templates/
     * ├── home/               (Páginas públicas y panel)
     * │   ├── index.html      (/home, /)
     * │   ├── login.html      (/login)
     * │   ├── registro.html   (/registro)
     * │   └── panel.html      (/panel)
     * ├── paciente/           (Gestión de pacientes)
     * │   ├── index.html      (/paciente)
     * │   ├── detalle.html    (/paciente/detalle)
     * │   └── nuevo.html      (/paciente/nuevo)
     * ├── cita/               (Gestión de citas)
     * │   ├── index.html      (/cita)
     * │   ├── detalle.html    (/cita/detalle)
     * │   └── nuevo.html      (/cita/nuevo)
     * ├── factura/            (Gestión de facturas - Solo ADMIN)
     * │   ├── index.html      (/factura)
     * │   ├── detalle.html    (/factura/detalle)
     * │   └── nuevo.html      (/factura/nuevo)
     * ├── registro-medico/    (Registros médicos - VET y ADMIN)
     * │   ├── index.html      (/registro-medico)
     * │   ├── detalle.html    (/registro-medico/detalle)
     * │   └── nuevo.html      (/registro-medico/nuevo)
     * └── acceso-denegado.html (/acceso-denegado)
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        // ========================================
                        // RUTAS PÚBLICAS
                        // ========================================
                        .requestMatchers("/", "/home", "/login", "/registro", "/acceso-denegado").permitAll()
                        .requestMatchers("/app.html").permitAll()

                        // ========================================
                        // ARCHIVOS ESTÁTICOS (CSS, JS)
                        // ========================================
                        .requestMatchers("/*.css", "/*.js").permitAll()

                        // ========================================
                        // RUTAS PRIVADAS (Seguridad en cliente con JWT)
                        // ========================================
                        // Panel principal
                        .requestMatchers("/panel").permitAll()

                        // Módulo Pacientes (Todos los usuarios autenticados)
                        .requestMatchers("/paciente/**").permitAll()

                        // Módulo Citas (Todos los usuarios autenticados)
                        .requestMatchers("/cita/**").permitAll()

                        // Módulo Facturas (Solo ADMIN - validado en cliente)
                        .requestMatchers("/factura/**").permitAll()

                        // Módulo Registros Médicos (VET y ADMIN - validado en cliente)
                        .requestMatchers("/registro-medico/**").permitAll()

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/panel", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/home")
                        .permitAll()
                )
                .exceptionHandling((exceptions) -> exceptions
                        .accessDeniedPage("/acceso-denegado")
                );
        return http.build();
    }

    /**
     * Encoder de contraseñas usando BCrypt.
     * BCrypt es un algoritmo de hash seguro para contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

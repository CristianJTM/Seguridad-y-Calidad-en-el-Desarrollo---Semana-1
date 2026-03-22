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

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public WebSecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    // Cadena de seguridad para las APIs REST (JWT, stateless)
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/pacientes/**").hasAnyRole("USER", "VET", "ADMIN")
                        .requestMatchers("/api/citas/**").hasAnyRole("USER", "VET", "ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Cadena de seguridad para las vistas Thymeleaf (sesión, form login)
    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/acceso-denegado", "/app.html").permitAll()
                        .requestMatchers("/*.css").permitAll()
                        .requestMatchers("/pacientes/**").hasAnyRole("USER", "VET", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/citas").hasAnyRole("USER", "VET", "ADMIN")
                        .requestMatchers("/citas/**").hasAnyRole("USER", "VET", "ADMIN")
                        .requestMatchers("/registros-medicos/**").hasAnyRole("VET", "ADMIN")
                        .requestMatchers("/facturas/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll())
                .exceptionHandling((exceptions) -> exceptions
                        .accessDeniedPage("/acceso-denegado")
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.duoc.veterinaria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/acceso-denegado").permitAll()
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

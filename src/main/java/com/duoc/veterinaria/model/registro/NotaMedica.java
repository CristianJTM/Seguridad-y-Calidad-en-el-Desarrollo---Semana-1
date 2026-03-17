package com.duoc.veterinaria.model.registro;

import java.time.LocalDateTime;

public class NotaMedica {

    private Long id;
    
    private String contenido;
    
    private LocalDateTime fecha;
    
    private String autor;

    public NotaMedica() {
    }

    public NotaMedica(String contenido, LocalDateTime fecha, String autor) {
        this.contenido = contenido;
        this.fecha = fecha;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}

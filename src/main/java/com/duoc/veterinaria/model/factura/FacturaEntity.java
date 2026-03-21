package com.duoc.veterinaria.model.factura;

import com.duoc.veterinaria.model.paciente.Paciente;
import jakarta.persistence.*;

@Entity
@Table(name = "factura")
public class FacturaEntity implements Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private double total;
    
    private String descripcion;

    private String veterinarioResponsable;

    private String notas;

    public FacturaEntity() {
    }

    public FacturaEntity(Paciente paciente, Factura factura) {
        this.paciente = paciente;
        this.total = factura.getCosto();
        this.descripcion = factura.getDescripcion();
    }

    public Long getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public double getCosto() {
        return total;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVeterinarioResponsable() {
        return veterinarioResponsable;
    }

    public void setVeterinarioResponsable(String veterinarioResponsable) {
        this.veterinarioResponsable = veterinarioResponsable;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}

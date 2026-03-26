package com.duoc.veterinaria.model.factura;

import com.duoc.veterinaria.model.paciente.Paciente;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "facturas")
public class FacturaEntity implements Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Transient
    private Factura factura;

    private double total;

    @Column(length = 1000)
    private String descripcion;

    @Column(name = "veterinario_responsable")
    private String veterinarioResponsable;

    @Column(length = 500)
    private String notas;

    @Column(name = "numero_factura")
    private String numeroFactura;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    private String estado;

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

    public Factura getFactura() {
        if (factura == null && total > 0) {
            // Reconstruir desde los valores persistidos en BD
            final double costoGuardado = this.total;
            final String descGuardada = this.descripcion;
            factura = new Factura() {
                public String getDescripcion() { return descGuardada; }
                public double getCosto() { return costoGuardado; }
            };
        }
        return factura;
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

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

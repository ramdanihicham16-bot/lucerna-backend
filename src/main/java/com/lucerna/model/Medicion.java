package com.lucerna.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Medicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private Double valorNdvi;
    private String nombreFinca;

    public Medicion() {}

    public Medicion(LocalDateTime fecha, Double valorNdvi, String nombreFinca) {
        this.fecha = fecha;
        this.valorNdvi = valorNdvi;
        this.nombreFinca = nombreFinca;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public Double getValorNdvi() { return valorNdvi; }
    public void setValorNdvi(Double valorNdvi) { this.valorNdvi = valorNdvi; }
    public String getNombreFinca() { return nombreFinca; }
    public void setNombreFinca(String nombreFinca) { this.nombreFinca = nombreFinca; }
}

package com.lucerna.model;

import jakarta.persistence.*;

@Entity
public class Finca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String polygonId;
    private String nombre;
    private String estado;
    private double nubes;
    private String tipoCultivo;

    // Coordenadas
    private double lat;
    private double lon;

    // Resultados IA
    private String ultimaRecomendacion;
    private double ultimoNdvi;

    public Finca() {}

    // Constructor 1: Básico
    public Finca(String nombre, String polygonId) {
        this.nombre = nombre;
        this.polygonId = polygonId;
    }

    // 👇 ESTE ES EL QUE TE FALTA (EL DE 4 DATOS) 👇
    public Finca(String nombre, String polygonId, double lat, double lon) {
        this.nombre = nombre;
        this.polygonId = polygonId;
        this.lat = lat;
        this.lon = lon;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPolygonId() { return polygonId; }
    public void setPolygonId(String polygonId) { this.polygonId = polygonId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public double getNubes() { return nubes; }
    public void setNubes(double nubes) { this.nubes = nubes; }
    public String getTipoCultivo() { return tipoCultivo; }
    public void setTipoCultivo(String tipoCultivo) { this.tipoCultivo = tipoCultivo; }
    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }
    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }
    public String getUltimaRecomendacion() { return ultimaRecomendacion; }
    public void setUltimaRecomendacion(String ultimaRecomendacion) { this.ultimaRecomendacion = ultimaRecomendacion; }
    public double getUltimoNdvi() { return ultimoNdvi; }
    public void setUltimoNdvi(double ultimoNdvi) { this.ultimoNdvi = ultimoNdvi; }
}
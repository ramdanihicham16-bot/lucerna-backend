package com.lucerna.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registros_satelitales")
public class RegistroSatelital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private Double ndvi;

    private String urlImagen;

    private String recomendacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finca_id")
    private Finca finca;

    public RegistroSatelital() {}

    public RegistroSatelital(Long id, LocalDateTime fecha, Double ndvi, String urlImagen, String recomendacion, Finca finca) {
        this.id = id;
        this.fecha = fecha;
        this.ndvi = ndvi;
        this.urlImagen = urlImagen;
        this.recomendacion = recomendacion;
        this.finca = finca;
    }

    public static RegistroSatelitalBuilder builder() {
        return new RegistroSatelitalBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public Double getNdvi() { return ndvi; }
    public void setNdvi(Double ndvi) { this.ndvi = ndvi; }
    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    public String getRecomendacion() { return recomendacion; }
    public void setRecomendacion(String recomendacion) { this.recomendacion = recomendacion; }
    public Finca getFinca() { return finca; }
    public void setFinca(Finca finca) { this.finca = finca; }

    public static class RegistroSatelitalBuilder {
        private Long id;
        private LocalDateTime fecha;
        private Double ndvi;
        private String urlImagen;
        private String recomendacion;
        private Finca finca;

        RegistroSatelitalBuilder() {}

        public RegistroSatelitalBuilder id(Long id) { this.id = id; return this; }
        public RegistroSatelitalBuilder fecha(LocalDateTime fecha) { this.fecha = fecha; return this; }
        public RegistroSatelitalBuilder ndvi(Double ndvi) { this.ndvi = ndvi; return this; }
        public RegistroSatelitalBuilder urlImagen(String urlImagen) { this.urlImagen = urlImagen; return this; }
        public RegistroSatelitalBuilder recomendacion(String recomendacion) { this.recomendacion = recomendacion; return this; }
        public RegistroSatelitalBuilder finca(Finca finca) { this.finca = finca; return this; }

        public RegistroSatelital build() {
            return new RegistroSatelital(id, fecha, ndvi, urlImagen, recomendacion, finca);
        }
    }
}

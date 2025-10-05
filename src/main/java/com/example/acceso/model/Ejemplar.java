package com.example.acceso.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ejemplar")
public class Ejemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejemplar")
    private Integer id_ejemplar;

    @Column(name = "cantidad_ejemplar", nullable = false)
    private Integer cantidadEjemplar;

    @Column(name = "localizacion", nullable = false, length = 150)
    private String localizacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    public Ejemplar() {}

    // Getters y Setters
    public Integer getId_ejemplar() {
        return id_ejemplar;
    }

    public void setIdEjemplar(Integer id_ejemplar) {
        this.id_ejemplar = id_ejemplar;
    }

    public Integer getCantidadEjemplar() {
        return cantidadEjemplar;
    }

    public void setCantidadEjemplar(Integer cantidadEjemplar) {
        this.cantidadEjemplar = cantidadEjemplar;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}

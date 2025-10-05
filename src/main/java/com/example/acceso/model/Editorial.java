package com.example.acceso.model;

import jakarta.persistence.*;

@Entity
@Table(name = "editorial")
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_editorial")
    private Long id_editorial;

    @Column(nullable = false, length = 100)
    private String nombre_editorial;

    public Long getId_editorial() {
        return id_editorial;
    }

    public void setId_editorial(Long id_editorial) {
        this.id_editorial = id_editorial;
    }

    public String getNombre_editorial() {
        return nombre_editorial;
    }

    public void setNombre_editorial(String nombre_editorial) {
        this.nombre_editorial = nombre_editorial;
    }
}



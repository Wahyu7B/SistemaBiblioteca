package com.example.acceso.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bibliotecario")
public class Bibliotecario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_bibliotecario", nullable = false, length = 100)
    private String nombre;

    @Column(name = "clave_bibliotecario", nullable = false, length = 50)
    private String clave;

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    public Bibliotecario() {}

    public Bibliotecario(String nombre, String clave, Perfil perfil) {
        this.nombre = nombre;
        this.clave = clave;
        this.perfil = perfil;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
}

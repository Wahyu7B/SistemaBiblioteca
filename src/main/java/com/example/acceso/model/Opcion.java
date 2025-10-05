package com.example.acceso.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "opciones")
public class Opcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String ruta;

    @Column(length = 50)
    private String icono;

    @ManyToMany(mappedBy = "opciones")
    @JsonIgnoreProperties("opciones")
    private Set<Perfil> perfiles = new HashSet<>();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }

    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }

    public Set<Perfil> getPerfiles() { return perfiles; }
    public void setPerfiles(Set<Perfil> perfiles) { this.perfiles = perfiles; }

    @Override
    public String toString() {
        return "Opcion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ruta='" + ruta + '\'' +
                ", icono='" + icono + '\'' +
                '}';
    }
}

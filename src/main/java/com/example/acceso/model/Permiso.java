package com.example.acceso.model;

import jakarta.persistence.*;

@Entity
@Table(name = "permiso")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPermiso;

    @Column(nullable = false, length = 100)
    private String nombrePermiso;

    public Permiso() {}

    public Permiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }

    public Long getIdPermiso() { return idPermiso; }
    public void setIdPermiso(Long idPermiso) { this.idPermiso = idPermiso; }

    public String getNombrePermiso() { return nombrePermiso; }
    public void setNombrePermiso(String nombrePermiso) { this.nombrePermiso = nombrePermiso; }

    @Override
    public String toString() {
        return "Permiso{" +
                "idPermiso=" + idPermiso +
                ", nombrePermiso='" + nombrePermiso + '\'' +
                '}';
    }
}

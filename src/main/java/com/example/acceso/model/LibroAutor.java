package com.example.acceso.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libro_autor")
public class LibroAutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro_por_autor")
    private Integer idLibroPorAutor;  // 👈 camelCase en Java

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_libro")
    private Libro libro;

    // Constructor vacío
    public LibroAutor() {}

    // Constructor con parámetros
    public LibroAutor(Autor autor, Libro libro) {
        this.autor = autor;
        this.libro = libro;
    }

    // Getters y Setters
    public Integer getIdLibroPorAutor() {
        return idLibroPorAutor;
    }

    public void setIdLibroPorAutor(Integer idLibroPorAutor) {
        this.idLibroPorAutor = idLibroPorAutor;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}

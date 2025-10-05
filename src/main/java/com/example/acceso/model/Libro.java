package com.example.acceso.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer id_libro;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "id_editorial")
    private Editorial editorial;

    @ManyToOne
    @JoinColumn(name = "id_genero")
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @Column(name = "estado")
    private String estado; 


    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LibroAutor> libroAutores = new ArrayList<>();


    public Integer getId_libro() { return id_libro; }
    public void setId_libro(Integer id_libro) { this.id_libro = id_libro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Editorial getEditorial() { return editorial; }
    public void setEditorial(Editorial editorial) { this.editorial = editorial; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<LibroAutor> getLibroAutores() { return libroAutores; }
    public void setLibroAutores(List<LibroAutor> libroAutores) { this.libroAutores = libroAutores; }


    public void addAutor(Autor autor) {
        LibroAutor la = new LibroAutor();
        la.setAutor(autor);
        la.setLibro(this);
        this.libroAutores.add(la);
    }

    public void removeAutor(Autor autor) {
        libroAutores.removeIf(la -> la.getAutor() != null && la.getAutor().getId_autor().equals(autor.getId_autor()));
    }
}

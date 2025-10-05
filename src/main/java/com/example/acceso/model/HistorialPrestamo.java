package com.example.acceso.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "historial_prestamos")
public class HistorialPrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_prestamo")
    private Prestamo prestamo;

    @ManyToOne
    @JoinColumn(name = "id_ejemplar")
    private Ejemplar ejemplar;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_real_devolucion", nullable = false)
    private LocalDate fechaRealDevolucion;

    public HistorialPrestamo() {}

    public HistorialPrestamo(Prestamo prestamo, Ejemplar ejemplar, Usuario usuario,
                             LocalDate fechaPrestamo, LocalDate fechaRealDevolucion) {
        this.prestamo = prestamo;
        this.ejemplar = ejemplar;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaRealDevolucion = fechaRealDevolucion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Prestamo getPrestamo() { return prestamo; }
    public void setPrestamo(Prestamo prestamo) { this.prestamo = prestamo; }

    public Ejemplar getEjemplar() { return ejemplar; }
    public void setEjemplar(Ejemplar ejemplar) { this.ejemplar = ejemplar; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaRealDevolucion() { return fechaRealDevolucion; }
    public void setFechaRealDevolucion(LocalDate fechaRealDevolucion) { this.fechaRealDevolucion = fechaRealDevolucion; }
}

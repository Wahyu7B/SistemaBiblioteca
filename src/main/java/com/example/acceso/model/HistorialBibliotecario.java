package com.example.acceso.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "historial_bibliotecario")
public class HistorialBibliotecario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_bibliotecario")
    private Bibliotecario bibliotecario;

    @Column(name = "fecha_acceso", nullable = false)
    private LocalDate fechaAcceso;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Column(name = "hora_entrada", nullable = false)
    private LocalTime horaEntrada;

    @Column(name = "hora_salida", nullable = false)
    private LocalTime horaSalida;

    public HistorialBibliotecario() {}

    public HistorialBibliotecario(Bibliotecario bibliotecario, LocalDate fechaAcceso,
                                  LocalDate fechaSalida, LocalTime horaEntrada, LocalTime horaSalida) {
        this.bibliotecario = bibliotecario;
        this.fechaAcceso = fechaAcceso;
        this.fechaSalida = fechaSalida;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Bibliotecario getBibliotecario() { return bibliotecario; }
    public void setBibliotecario(Bibliotecario bibliotecario) { this.bibliotecario = bibliotecario; }

    public LocalDate getFechaAcceso() { return fechaAcceso; }
    public void setFechaAcceso(LocalDate fechaAcceso) { this.fechaAcceso = fechaAcceso; }

    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    public LocalTime getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(LocalTime horaEntrada) { this.horaEntrada = horaEntrada; }

    public LocalTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalTime horaSalida) { this.horaSalida = horaSalida; }
}

package com.example.acceso.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal multa;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(nullable = false)
    private int estado; // 0: pendiente, 1: pagado

    @ManyToOne
    @JoinColumn(name = "id_prestamo")
    private Prestamo prestamo;

    public Pago() {}

    public Pago(BigDecimal multa, LocalDateTime fechaPago, int estado, Prestamo prestamo) {
        this.multa = multa;
        this.fechaPago = fechaPago;
        this.estado = estado;
        this.prestamo = prestamo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getMulta() { return multa; }
    public void setMulta(BigDecimal multa) { this.multa = multa; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    public Prestamo getPrestamo() { return prestamo; }
    public void setPrestamo(Prestamo prestamo) { this.prestamo = prestamo; }
}

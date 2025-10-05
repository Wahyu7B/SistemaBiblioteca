package com.example.acceso.repository;

import com.example.acceso.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    long countByEstado(int estado); // 0 = pendiente, 1 = pagado (ajusta a tu convención)
}

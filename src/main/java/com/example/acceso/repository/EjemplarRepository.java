package com.example.acceso.repository;

import com.example.acceso.model.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {

    // Ajusta el nombre del campo si en tu entidad se llama distinto:
    // cantidadEjemplar  -> cámbialo a "cantidad" o "stock" si así está en tu modelo.
    @Query("select coalesce(sum(e.cantidadEjemplar), 0) from Ejemplar e")
    Long sumCantidad();
}

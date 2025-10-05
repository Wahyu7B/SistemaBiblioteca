package com.example.acceso.repository;

import com.example.acceso.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    long countByFechaDevolucionGreaterThanEqual(LocalDate hoy);
    long countByFechaPrestamo(LocalDate hoy);
    List<Prestamo> findTop8ByOrderByIdDesc();

    // Top libros por cantidad de préstamos (se cuenta por el libro del ejemplar)
    @Query("""
           select p.ejemplar.libro.id_libro as idLibro,
                  p.ejemplar.libro.titulo as titulo,
                  count(p.id) as prestamos
           from Prestamo p
           group by p.ejemplar.libro.id_libro, p.ejemplar.libro.titulo
           order by count(p.id) desc
           """)
    List<Object[]> topLibros(Pageable pageable);
}

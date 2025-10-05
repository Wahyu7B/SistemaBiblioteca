package com.example.acceso.service.impl;

import com.example.acceso.model.Prestamo;
import com.example.acceso.model.Usuario;
import com.example.acceso.repository.*;
import com.example.acceso.service.StatsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class StatsServiceImpl implements StatsService {

    private final UsuarioRepository usuarioRepo;
    private final PrestamoRepository prestamoRepo;
    private final PagoRepository pagoRepo;
    private final EjemplarRepository ejemplarRepo;

    public StatsServiceImpl(UsuarioRepository usuarioRepo,
                            PrestamoRepository prestamoRepo,
                            PagoRepository pagoRepo,
                            EjemplarRepository ejemplarRepo) {
        this.usuarioRepo = usuarioRepo;
        this.prestamoRepo = prestamoRepo;
        this.pagoRepo = pagoRepo;
        this.ejemplarRepo = ejemplarRepo;
    }

    @Override public boolean esAdmin(Usuario u) {
        String nombrePerfil = (u.getPerfil()!=null? u.getPerfil().getNombre() : "");
        return nombrePerfil != null && (nombrePerfil.equalsIgnoreCase("ADMIN") || nombrePerfil.equalsIgnoreCase("BIBLIOTECARIO"));
    }

    @Override public long totalUsuarios() { return usuarioRepo.countByEstadoNot(2); }

    @Override public long totalLibrosDisponibles() {
        // Opción A: total de ejemplares
        Long stock = ejemplarRepo.sumCantidad();
        return stock != null ? stock : 0L;
        // Opción B (si usas estado en Libro): return libroRepo.countByEstadoIgnoreCase("disponible");
    }

    @Override public long prestamosActivos() {
        LocalDate hoy = LocalDate.now();
        return prestamoRepo.countByFechaDevolucionGreaterThanEqual(hoy);
    }

    @Override public long morasPendientes() { return pagoRepo.countByEstado(0); }

    @Override public List<Prestamo> ultimosPrestamos(int limit) {
        return prestamoRepo.findTop8ByOrderByIdDesc();
    }

    @Override public List<Map<String, Object>> topLibrosPrestados(int limit) {
        var rows = prestamoRepo.topLibros(PageRequest.of(0, limit));
        List<Map<String,Object>> out = new ArrayList<>();
        for (Object[] r : rows) {
            Map<String,Object> m = new HashMap<>();
            m.put("idLibro", r[0]);
            m.put("titulo", r[1]);
            m.put("prestamos", ((Number) r[2]).longValue());
            out.add(m);
        }
        return out;
    }

    @Override public List<?> recomendadosParaUsuario(Usuario u, int limit) {
        // De momento vacío; puedes implementar por género/categoría del usuario
        return List.of();
    }
}

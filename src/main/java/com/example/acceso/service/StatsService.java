package com.example.acceso.service;

import com.example.acceso.model.Prestamo;
import com.example.acceso.model.Usuario;

import java.util.List;
import java.util.Map;

public interface StatsService {
    boolean esAdmin(Usuario u);

    long totalUsuarios();
    long totalLibrosDisponibles();
    long prestamosActivos();
    long morasPendientes();

    List<Prestamo> ultimosPrestamos(int limit);

    // cada item: { "titulo": String, "prestamos": Long }
    List<Map<String,Object>> topLibrosPrestados(int limit);

    // placeholder para landing/usuario
    List<?> recomendadosParaUsuario(Usuario u, int limit);
}

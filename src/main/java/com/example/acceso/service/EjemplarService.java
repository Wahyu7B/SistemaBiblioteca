package com.example.acceso.service;

import com.example.acceso.model.Ejemplar;
import com.example.acceso.repository.EjemplarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EjemplarService {

    private final EjemplarRepository ejemplarRepository;

    public EjemplarService(EjemplarRepository ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    // Guardar un ejemplar
    public Ejemplar guardar(Ejemplar ejemplar) {
        return ejemplarRepository.save(ejemplar);
    }

    // Obtener todos los ejemplares
    public List<Ejemplar> encontrarTodos() {
        return ejemplarRepository.findAll();
    }

    // Buscar un ejemplar por ID
    public Optional<Ejemplar> encontrarPorId(Long idEjemplar) {
        return ejemplarRepository.findById(idEjemplar);
    }

    // Eliminar un ejemplar por ID
    public void eliminarPorId(Long idEjemplar) {
        ejemplarRepository.deleteById(idEjemplar);
    }

    // Verificar si existe un ejemplar por ID
    public boolean existePorId(Long idEjemplar) {
        return ejemplarRepository.existsById(idEjemplar);
    }
}

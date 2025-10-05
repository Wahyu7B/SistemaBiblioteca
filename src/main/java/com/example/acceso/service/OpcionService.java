package com.example.acceso.service;

import com.example.acceso.model.Opcion;
import com.example.acceso.repository.OpcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OpcionService {

    private final OpcionRepository opcionRepository;

    public OpcionService(OpcionRepository opcionRepository) {
        this.opcionRepository = opcionRepository;
    }

    public List<Opcion> listarTodos() {
        return opcionRepository.findAll();
    }

    public Optional<Opcion> buscarPorId(Long id) {
        return opcionRepository.findById(id);
    }

    public Opcion guardar(Opcion opcion) {
        return opcionRepository.save(opcion);
    }

    public void eliminar(Long id) {
        opcionRepository.deleteById(id);
    }
}

package com.example.acceso.service;

import com.example.acceso.model.LibroAutor;
import com.example.acceso.repository.LibroAutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroAutorService {

    private final LibroAutorRepository libroAutorRepository;

    // Inyección de dependencias por constructor
    public LibroAutorService(LibroAutorRepository libroAutorRepository) {
        this.libroAutorRepository = libroAutorRepository;
    }

    // Guardar o actualizar
    public LibroAutor save(LibroAutor libroAutor) {
        return libroAutorRepository.save(libroAutor);
    }

    // Obtener todos
    public List<LibroAutor> findAll() {
        return libroAutorRepository.findAll();
    }

    // Buscar por ID
    public Optional<LibroAutor> findById(Integer id) {
        return libroAutorRepository.findById(id);
    }

    // Eliminar por ID
    public void deleteById(Integer id) {
        libroAutorRepository.deleteById(id);
    }

    // Verificar existencia por ID
    public boolean existsById(Integer id) {
        return libroAutorRepository.existsById(id);
    }
}

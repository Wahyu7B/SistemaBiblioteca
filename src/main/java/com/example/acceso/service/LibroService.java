package com.example.acceso.service;

import com.example.acceso.model.Libro;
import com.example.acceso.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> listar() {
        return libroRepository.findAll();
    }

    public Optional<Libro> buscarPorId(Integer id_libro) {
        return libroRepository.findById(id_libro);
    }

    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    public void eliminarPorId(Integer id_libro) {
        libroRepository.deleteById(id_libro);
    }

    public boolean existePorId(Integer id_libro) {
        return libroRepository.existsById(id_libro);
    }

    public List<Libro> encontrarTodos() {
        return libroRepository.findAll();
    }

}


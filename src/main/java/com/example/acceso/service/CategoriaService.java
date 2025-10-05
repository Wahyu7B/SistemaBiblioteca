package com.example.acceso.service;

import com.example.acceso.model.Categoria;
import com.example.acceso.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findById(Long id_categoria) {
        return categoriaRepository.findById(id_categoria);
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteById(Long id_categoria) {
        categoriaRepository.deleteById(id_categoria);
    }

    public boolean existsById(Long id_categoria) {
        return categoriaRepository.existsById(id_categoria);
    }
}

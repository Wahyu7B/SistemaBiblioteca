package com.example.acceso.service;

import com.example.acceso.model.Editorial;
import com.example.acceso.repository.EditorialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialService {

    private final EditorialRepository editorialRepository;

    public EditorialService(EditorialRepository editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

    public List<Editorial> encontrarTodas() {
        return editorialRepository.findAll();
    }

    public Optional<Editorial> encontrarPorId(Long idEditorial) {
        return editorialRepository.findById(idEditorial);
    }

    public Editorial guardar(Editorial editorial) {
        return editorialRepository.save(editorial);
    }

    public void eliminarPorId(Long idEditorial) {
        editorialRepository.deleteById(idEditorial);
    }

    public boolean existePorId(Long idEditorial) {
        return editorialRepository.existsById(idEditorial);
    }
}

package com.example.acceso.service;

import com.example.acceso.model.Autor;
import com.example.acceso.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public Optional<Autor> findById(Long id_autor) {
        return autorRepository.findById(id_autor);
    }

    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    public void deleteById(Long id_autor) {
        autorRepository.deleteById(id_autor);
    }

    public boolean existsById(Long id_autor) {
        return autorRepository.existsById(id_autor);
    }
}

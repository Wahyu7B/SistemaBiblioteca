package com.example.acceso.service;

import com.example.acceso.model.Genero;
import com.example.acceso.repository.GeneroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    public Optional<Genero> findById(Long id_genero) {
        return generoRepository.findById(id_genero);
    }

    public Genero save(Genero genero) {
        return generoRepository.save(genero);
    }

    public void deleteById(Long id_genero) {
        generoRepository.deleteById(id_genero);
    }

    public boolean existsById(Long id_genero) {
        return generoRepository.existsById(id_genero);
    }
}

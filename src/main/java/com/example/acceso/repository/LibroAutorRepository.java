package com.example.acceso.repository;

import com.example.acceso.model.LibroAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroAutorRepository extends JpaRepository<LibroAutor, Integer> {
}

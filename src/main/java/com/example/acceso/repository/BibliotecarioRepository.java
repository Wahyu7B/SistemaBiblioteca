package com.example.acceso.repository;

import com.example.acceso.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
}


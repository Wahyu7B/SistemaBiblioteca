package com.example.acceso.controller;

import com.example.acceso.model.Libro;
import com.example.acceso.service.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public List<Libro> listar() {
        return libroService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscarPorId(@PathVariable Integer id) {
        return libroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Libro guardar(@RequestBody Libro libro) {
        return libroService.guardar(libro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Integer id, @RequestBody Libro libro) {
        if (!libroService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        libro.setId_libro(id); // 👈 aquí ahora encaja bien
        return ResponseEntity.ok(libroService.guardar(libro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!libroService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        libroService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}

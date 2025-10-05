package com.example.acceso.controller;

import com.example.acceso.model.Autor;
import com.example.acceso.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public List<Autor> listar() {
        return autorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> obtener(@PathVariable Long id) {
        return autorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Autor crear(@RequestBody Autor autor) {
        return autorService.save(autor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autor> actualizar(@PathVariable Long id, @RequestBody Autor autor) {
        if (!autorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        autor.setId_autor(id);
        return ResponseEntity.ok(autorService.save(autor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!autorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        autorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

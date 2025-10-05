package com.example.acceso.controller;

import com.example.acceso.model.Genero;
import com.example.acceso.service.GeneroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @GetMapping
    public List<Genero> listar() {
        return generoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> obtener(@PathVariable Long id) {
        return generoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Genero crear(@RequestBody Genero genero) {
        return generoService.save(genero);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> actualizar(@PathVariable Long id, @RequestBody Genero genero) {
        if (!generoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        genero.setId_genero(id);
        return ResponseEntity.ok(generoService.save(genero));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!generoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        generoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

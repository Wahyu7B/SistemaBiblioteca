package com.example.acceso.controller;

import com.example.acceso.model.Editorial;
import com.example.acceso.service.EditorialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editoriales")
public class EditorialController {

    private final EditorialService editorialService;

    public EditorialController(EditorialService editorialService) {
        this.editorialService = editorialService;
    }

    @GetMapping
    public List<Editorial> listarTodas() {
        return editorialService.encontrarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Editorial> obtenerPorId(@PathVariable Long id) {
        return editorialService.encontrarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Editorial crear(@RequestBody Editorial editorial) {
        return editorialService.guardar(editorial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Editorial> actualizar(@PathVariable Long id, @RequestBody Editorial editorial) {
        if (!editorialService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        editorial.setId_editorial(id);  // Mantener nombre de campo en modelo
        return ResponseEntity.ok(editorialService.guardar(editorial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        if (!editorialService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        editorialService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}

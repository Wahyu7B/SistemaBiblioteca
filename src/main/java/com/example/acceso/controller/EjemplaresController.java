package com.example.acceso.controller;

import com.example.acceso.model.Ejemplar;
import com.example.acceso.service.EjemplarService;
import com.example.acceso.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ejemplares")
public class EjemplaresController {

    @Autowired
    private EjemplarService ejemplarService;

    @Autowired
    private LibroService libroService;

    // Listar todos los ejemplares
    @GetMapping
    public String listarTodos(Model model) {
        model.addAttribute("ejemplares", ejemplarService.encontrarTodos());
        model.addAttribute("ejemplarForm", new Ejemplar());
        model.addAttribute("libros", libroService.encontrarTodos());
        return "ejemplares";
    }

    // Guardar un ejemplar
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Ejemplar ejemplar) {
        ejemplarService.guardar(ejemplar);
        return "redirect:/ejemplares";
    }

    // Editar un ejemplar por ID
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("ejemplarForm", ejemplarService.encontrarPorId(id).orElse(new Ejemplar()));
        model.addAttribute("ejemplares", ejemplarService.encontrarTodos());
        model.addAttribute("libros", libroService.encontrarTodos());
        return "ejemplares";
    }

    // Eliminar un ejemplar por ID
    @GetMapping("/eliminar/{id}")
    public String eliminarPorId(@PathVariable Long id) {
        ejemplarService.eliminarPorId(id);
        return "redirect:/ejemplares";
    }
}


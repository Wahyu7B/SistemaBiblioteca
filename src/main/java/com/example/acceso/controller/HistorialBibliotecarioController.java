package com.example.acceso.controller;


import com.example.acceso.model.HistorialBibliotecario;
import com.example.acceso.repository.HistorialBibliotecarioRepository;
import com.example.acceso.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/historial-bibliotecarios")
public class HistorialBibliotecarioController {

    @Autowired private HistorialBibliotecarioRepository historialBibliotecarioRepository;
    @Autowired private BibliotecarioRepository bibliotecarioRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("historiales", historialBibliotecarioRepository.findAll());
        model.addAttribute("historialForm", new HistorialBibliotecario());
        model.addAttribute("bibliotecarios", bibliotecarioRepository.findAll());
        return "historial_bibliotecarios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute HistorialBibliotecario historial) {
        historialBibliotecarioRepository.save(historial);
        return "redirect:/historial-bibliotecarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("historialForm", historialBibliotecarioRepository.findById(id).orElse(new HistorialBibliotecario()));
        model.addAttribute("historiales", historialBibliotecarioRepository.findAll());
        model.addAttribute("bibliotecarios", bibliotecarioRepository.findAll());
        return "historial_bibliotecarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        historialBibliotecarioRepository.deleteById(id);
        return "redirect:/historial-bibliotecarios";
    }
}

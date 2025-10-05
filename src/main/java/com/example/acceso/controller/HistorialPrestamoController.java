package com.example.acceso.controller;

import com.example.acceso.model.HistorialPrestamo;
import com.example.acceso.repository.HistorialPrestamoRepository;
import com.example.acceso.repository.EjemplarRepository;
import com.example.acceso.repository.UsuarioRepository;
import com.example.acceso.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/historial-prestamos")
public class HistorialPrestamoController {

    @Autowired private HistorialPrestamoRepository historialPrestamoRepository;
    @Autowired private PrestamoRepository prestamoRepository;
    @Autowired private EjemplarRepository ejemplarRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("historial", historialPrestamoRepository.findAll());
        model.addAttribute("historialForm", new HistorialPrestamo());
        model.addAttribute("prestamos", prestamoRepository.findAll());
        model.addAttribute("ejemplares", ejemplarRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "historial_prestamos";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute HistorialPrestamo historial) {
        historialPrestamoRepository.save(historial);
        return "redirect:/historial-prestamos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("historialForm", historialPrestamoRepository.findById(id).orElse(new HistorialPrestamo()));
        model.addAttribute("historial", historialPrestamoRepository.findAll());
        model.addAttribute("prestamos", prestamoRepository.findAll());
        model.addAttribute("ejemplares", ejemplarRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "historial_prestamos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        historialPrestamoRepository.deleteById(id);
        return "redirect:/historial-prestamos";
    }
}

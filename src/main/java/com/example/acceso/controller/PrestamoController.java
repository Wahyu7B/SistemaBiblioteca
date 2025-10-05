package com.example.acceso.controller;

import com.example.acceso.model.Prestamo;
import com.example.acceso.repository.EjemplarRepository;
import com.example.acceso.repository.PrestamoRepository;
import com.example.acceso.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired private PrestamoRepository prestamoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private EjemplarRepository ejemplarRepository;
    

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("prestamos", prestamoRepository.findAll());
        model.addAttribute("prestamoForm", new Prestamo());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("ejemplares", ejemplarRepository.findAll());
        return "prestamos";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Prestamo prestamo) {
        prestamoRepository.save(prestamo);
        return "redirect:/prestamos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("prestamoForm", prestamoRepository.findById(id).orElse(new Prestamo()));
        model.addAttribute("prestamos", prestamoRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("ejemplares", ejemplarRepository.findAll());
        return "prestamos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        prestamoRepository.deleteById(id);
        return "redirect:/prestamos";
    }
}


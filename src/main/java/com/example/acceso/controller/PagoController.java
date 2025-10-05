package com.example.acceso.controller;

import com.example.acceso.model.Pago;
import com.example.acceso.repository.PagoRepository;
import com.example.acceso.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pagos")
public class PagoController {

    @Autowired private PagoRepository pagoRepository;
    @Autowired private PrestamoRepository prestamoRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pagos", pagoRepository.findAll());
        model.addAttribute("pagoForm", new Pago());
        model.addAttribute("prestamos", prestamoRepository.findAll());
        return "pagos";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Pago pago) {
        pagoRepository.save(pago);
        return "redirect:/pagos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("pagoForm", pagoRepository.findById(id).orElse(new Pago()));
        model.addAttribute("pagos", pagoRepository.findAll());
        model.addAttribute("prestamos", prestamoRepository.findAll());
        return "pagos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        pagoRepository.deleteById(id);
        return "redirect:/pagos";
    }
}

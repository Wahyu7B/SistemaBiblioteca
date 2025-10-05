package com.example.acceso.controller;

import com.example.acceso.model.Usuario;
import com.example.acceso.service.StatsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final StatsService statsService;

    public AdminDashboardController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) return "redirect:/login";
        if (!statsService.esAdmin(u)) return "redirect:/usuario/inicio";

        model.addAttribute("totalUsuarios", statsService.totalUsuarios());
        model.addAttribute("librosDisponibles", statsService.totalLibrosDisponibles());
        model.addAttribute("prestamosActivos", statsService.prestamosActivos());
        model.addAttribute("morasPendientes", statsService.morasPendientes());

        model.addAttribute("ultimosPrestamos", statsService.ultimosPrestamos(8));
        model.addAttribute("topLibros", statsService.topLibrosPrestados(6));

        return "admin/dashboard";
    }
}

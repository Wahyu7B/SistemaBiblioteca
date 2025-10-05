package com.example.acceso.controller;

import com.example.acceso.model.Usuario;
import com.example.acceso.service.StatsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioInicioController {

    private final StatsService stats;
    public UsuarioInicioController(StatsService stats){ this.stats = stats; }

    @GetMapping("/usuario/inicio")
    public String inicioUsuario(Model model, HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) return "redirect:/login";
        if (stats.esAdmin(u)) return "redirect:/admin/dashboard";
        model.addAttribute("usuario", u);
        model.addAttribute("recomendados", stats.recomendadosParaUsuario(u, 6));
        return "usuario/inicio";
    }
}

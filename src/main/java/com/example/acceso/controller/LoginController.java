package com.example.acceso.controller;

import com.example.acceso.model.Opcion;
import com.example.acceso.model.Usuario;
import com.example.acceso.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.Optional;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;
    public LoginController(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @GetMapping("/login")
    public String mostrarFormularioLogin(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) return "redirect:/";
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
                                @RequestParam String clave,
                                HttpSession session,
                                RedirectAttributes ra) {

        Optional<Usuario> usuarioOpt = usuarioService.findByUsuario(usuario);
        if (usuarioOpt.isEmpty()) { ra.addFlashAttribute("error","Usuario no encontrado."); return "redirect:/login"; }

        Usuario u = usuarioOpt.get();
        if (u.getEstado() != 1) { ra.addFlashAttribute("error","Este usuario se encuentra inactivo."); return "redirect:/login"; }

        if (!usuarioService.verificarContrasena(clave, u.getClave())) {
            ra.addFlashAttribute("error","Contraseña incorrecta."); return "redirect:/login";
        }

        // ok
        session.setAttribute("usuarioLogueado", u);
        var opcionesMenu = u.getPerfil().getOpciones().stream()
                .sorted(Comparator.comparing(Opcion::getId)).toList();
        session.setAttribute("menuOpciones", opcionesMenu);

        // Redirección por perfil
        return esAdmin(u) ? "redirect:/admin/dashboard" : "redirect:/usuario/inicio";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes ra) {
        session.invalidate();
        ra.addFlashAttribute("logout","Has cerrado sesión exitosamente.");
        return "redirect:/login";
    }

    private boolean esAdmin(Usuario u) {
        String nombrePerfil = (u.getPerfil() != null ? u.getPerfil().getNombre() : "");
        return nombrePerfil != null && (
                nombrePerfil.equalsIgnoreCase("ADMIN") ||
                nombrePerfil.equalsIgnoreCase("BIBLIOTECARIO")
        );
    }
}

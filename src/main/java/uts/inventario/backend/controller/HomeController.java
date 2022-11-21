package uts.inventario.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uts.inventario.backend.entity.Usuario;
import uts.inventario.backend.exception.NotFoundException;
import uts.inventario.backend.repository.EquipoRepository;
import uts.inventario.backend.repository.ReporteRepository;
import uts.inventario.backend.repository.UsuarioRepository;
import uts.inventario.backend.service.UsuarioDetailsService;

@Controller
public class HomeController {
    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ReporteRepository reporteRepository;

    @GetMapping("/")
    public String indexTemplate() {
        return "redirect:/panel";
    }

    @GetMapping("/panel")
    public String panelTemplate(Model model, @AuthenticationPrincipal UsuarioDetailsService user) {
        model.addAttribute("equipos", equipoRepository.findAll());
        Usuario usuario = usuarioRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        if (usuario.getRol().getNombre().equals("CLIENTE")) {
            model.addAttribute("reportes", reporteRepository.getReportesByUsuario(usuario));
        }else {
            model.addAttribute("reportes", reporteRepository.findAll());
        }
        return "panel";
    }

    @GetMapping("/login")
    public String loginTemplate() {
        return "login";
    }

    @GetMapping("/register")
    public String registerTemplate(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }
}
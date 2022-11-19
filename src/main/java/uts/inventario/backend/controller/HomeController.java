package uts.inventario.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uts.inventario.backend.entity.Usuario;

@Controller
public class HomeController {
    @GetMapping("/")
    public String indexTemplate() {
        return "redirect:/panel";
    }

    @GetMapping("/panel")
    public String panelTemplate() {
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
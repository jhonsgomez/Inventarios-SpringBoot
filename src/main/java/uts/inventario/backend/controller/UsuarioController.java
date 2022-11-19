package uts.inventario.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uts.inventario.backend.entity.Rol;
import uts.inventario.backend.entity.Usuario;
import uts.inventario.backend.exception.NotFoundException;
import uts.inventario.backend.repository.RolRepository;
import uts.inventario.backend.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    @PostMapping("/")
    public String saveUsuario(@ModelAttribute("usuario") Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Rol rol = rolRepository.findById(2).orElseThrow(() -> new NotFoundException("Rol No encontrado"));
        usuario.setContrasena(encoder.encode(usuario.getContrasena()));
        usuario.setRol(rol);
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }
}

package uts.inventario.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uts.inventario.backend.entity.Equipo;
import uts.inventario.backend.exception.NotFoundException;
import uts.inventario.backend.repository.CategoriaRepository;
import uts.inventario.backend.repository.EquipoRepository;
import uts.inventario.backend.repository.EstadoRepository;

@Controller
@RequestMapping("/equipos")
public class EquiposController {
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private EquipoRepository equipoRepository;

    @GetMapping("/")
    public String equiposListTemplate(Model model) {
        model.addAttribute("equipos", equipoRepository.findAll());
        return "equipos-list";
    }

    @GetMapping("/new")
    public String equiposCreateTemplate(Model model) {
        Equipo equipo = new Equipo();
        equipo.setImagen("");
        model.addAttribute("equipo", equipo);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        return "equipos-form";
    }

    @PostMapping("/save")
    public String equiposCreateProcess(@ModelAttribute("equipo") Equipo equipo) {
        if (equipo.getImagen().equalsIgnoreCase("")) {
            equipo.setImagen("https://uxwing.com/wp-content/themes/uxwing/download/web-app-development/image-not-found-icon.png");
        }
        equipoRepository.save(equipo);
        return "redirect:/panel#modal_tabla_personal";
    }

    @GetMapping("/edit/{id}")
    public String equiposEditTemplate(@PathVariable Long id, Model model) {
        Equipo equipo = equipoRepository.findById(id).orElseThrow(() -> new NotFoundException("Equipo no encontrado"));
        model.addAttribute("equipo", equipo);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        return "equipos-form";
    }

    @GetMapping("/delete/{id}")
    public String equiposDeleteProcess(@PathVariable Long id) {
        equipoRepository.deleteById(id);
        return "redirect:/panel#modal_tabla_personal";
    }
}

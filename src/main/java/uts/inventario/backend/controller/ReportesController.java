package uts.inventario.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uts.inventario.backend.entity.Equipo;
import uts.inventario.backend.entity.Reporte;
import uts.inventario.backend.entity.Usuario;
import uts.inventario.backend.exception.NotFoundException;
import uts.inventario.backend.repository.EquipoRepository;
import uts.inventario.backend.repository.EstadoRepository;
import uts.inventario.backend.repository.ReporteRepository;
import uts.inventario.backend.repository.UsuarioRepository;
import uts.inventario.backend.service.UsuarioDetailsService;

import java.util.Date;

@Controller
@RequestMapping("/reportes")
public class ReportesController {
    @Autowired
    private ReporteRepository reporteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/")
    public String reportesListTemplate(Model model, @AuthenticationPrincipal UsuarioDetailsService user) {
        Usuario usuario = usuarioRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        if (usuario.getRol().getNombre().equals("CLIENTE")) {
            model.addAttribute("reportes", reporteRepository.getReportesByUsuario(usuario));
        }else {
            model.addAttribute("reportes", reporteRepository.findAll());
        }
        return "reportes-list";
    }

    @GetMapping("/new")
    public String reportesNewTemplate(Model model) {
        Reporte reporte = new Reporte();
        model.addAttribute("reporte", reporte);
        model.addAttribute("equipos", equipoRepository.findAll());
        return "reportes-form";
    }

    @PostMapping("/save")
    public String reportesSaveProcess(@ModelAttribute("reporte") Reporte reporte, @AuthenticationPrincipal UsuarioDetailsService user) {
        Usuario usuario = usuarioRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        Equipo equipo = reporte.getEquipo();
        equipo.setEstado(estadoRepository.findById(2L).orElseThrow(() -> new NotFoundException("Estado no encontrado")));
        equipoRepository.save(equipo);
        reporte.setUsuario(usuario);
        reporte.setEquipo(equipo);
        reporte.setFecha(new Date());
        reporteRepository.save(reporte);
        String asunto = "[UTS-REPORT SYSTEM]: Nuevo reporte encontrado";
        String cuerpo = "Cordial Saludo.\n\nSe evidencio un nuevo reporte en el sistema de inventario tecnologico con la siguiente informacion: \n\n - Codigo: " + reporte.getCodigo() + "\n\n - Fecha: " + reporte.getFecha() + "\n\n - Solicitante: " + reporte.getUsuario().getNombre() + "\n\n- Equipo Reportado: " + reporte.getEquipo().getCodigo() + "\n\n- Descripcion: " + reporte.getDescripcion();
        emailSend("jhonsebastiangomezsierra@gmail.com", asunto, cuerpo);
        return "redirect:/panel#modal_tabla_usuarios";
    }

    @GetMapping("/edit/{id}")
    public String reportesEditTemplate(@PathVariable Long id, Model model) {
        Reporte reporte = reporteRepository.findById(id).orElseThrow(() -> new NotFoundException("Reporte no encontrado"));
        model.addAttribute("reporte", reporte);
        model.addAttribute("equipos", equipoRepository.findAll());
        return "reportes-form";
    }

    @GetMapping("/check/{id}")
    public String reportesApproveProcess(@PathVariable Long id, @RequestParam String status) {
        Reporte reporte = reporteRepository.findById(id).orElseThrow(() -> new NotFoundException("Reporte no encontrado"));
        Equipo equipo = reporte.getEquipo();
        equipo.setEstado(estadoRepository.findById(1L).orElseThrow(() -> new NotFoundException("Estado no encontrado")));
        equipoRepository.save(equipo);
        String asunto;
        String cuerpo;
        if (status.equals("approve")) {
            asunto = "[UTS-REPORT SYSTEM]: Tu reporte ha sido aprobado";
            cuerpo = "Cordial Saludo.\n\nSe ha realizado la respesctiva verificacion de tu reporte con la siguiente informacion: \n\n - Codigo: " + reporte.getCodigo() + "\n\n - Fecha: " + reporte.getFecha() + "\n\n - Solicitante: " + reporte.getUsuario().getNombre() + "\n\n- Equipo Reportado: " + reporte.getEquipo().getCodigo() + "\n\n- Descripcion: " + reporte.getDescripcion() + "\n\nEl estado de tu solicitus es: [APROBADO]";
        }
        else {
            asunto = "[UTS-REPORT SYSTEM]: Tu reporte ha sido desaprobado";
            cuerpo = "Cordial Saludo.\n\nSe ha realizado la respesctiva verificacion de tu reporte con la siguiente informacion: \n\n - Codigo: " + reporte.getCodigo() + "\n\n - Fecha: " + reporte.getFecha() + "\n\n - Solicitante: " + reporte.getUsuario().getNombre() + "\n\n- Equipo Reportado: " + reporte.getEquipo().getCodigo() + "\n\n- Descripcion: " + reporte.getDescripcion() + "\n\nEl estado de tu solicitus es: [DESAPROBADO]";
        }
        emailSend(reporte.getUsuario().getCorreo(), asunto, cuerpo);
        reporteRepository.deleteById(id);
        return "redirect:/panel#modal_tabla_usuarios";
    }

    public void emailSend(String correo, String asunto, String cuerpo) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("gamesj917@gmail.com");
        mailMessage.setTo(correo);
        mailMessage.setSubject(asunto);
        mailMessage.setText(cuerpo);
        javaMailSender.send(mailMessage);
    }
}

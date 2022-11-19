package uts.inventario.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uts.inventario.backend.entity.Usuario;
import uts.inventario.backend.repository.UsuarioRepository;

public class UsuarioDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.getUsuarioByCorreo(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("El correo [ " + username + " ] no existe");
        }
        return new UsuarioDetailsService(usuario);
    }
}

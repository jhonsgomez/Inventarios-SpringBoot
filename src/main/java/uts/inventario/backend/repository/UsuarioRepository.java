package uts.inventario.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uts.inventario.backend.entity.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    @Query("SELECT usuario FROM Usuario usuario WHERE usuario.correo = :correo")
    Usuario getUsuarioByCorreo(@Param("correo") String correo);
}

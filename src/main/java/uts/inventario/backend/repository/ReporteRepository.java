package uts.inventario.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uts.inventario.backend.entity.Reporte;
import uts.inventario.backend.entity.Usuario;

import java.util.List;

@Repository
public interface ReporteRepository extends CrudRepository<Reporte, Long> {
    @Query("SELECT reporte FROM Reporte reporte WHERE reporte.usuario = :usuario")
    List<Reporte> getReportesByUsuario(@Param("usuario") Usuario usuario);
}

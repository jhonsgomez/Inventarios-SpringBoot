package uts.inventario.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uts.inventario.backend.entity.Equipo;

@Repository
public interface EquipoRepository extends CrudRepository<Equipo, Long> { }

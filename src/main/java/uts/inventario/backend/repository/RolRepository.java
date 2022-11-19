package uts.inventario.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uts.inventario.backend.entity.Rol;

@Repository
public interface RolRepository extends CrudRepository<Rol, Integer> { }

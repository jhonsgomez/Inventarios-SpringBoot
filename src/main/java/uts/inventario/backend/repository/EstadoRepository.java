package uts.inventario.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uts.inventario.backend.entity.Estado;

@Repository
public interface EstadoRepository extends CrudRepository<Estado, Long> { }

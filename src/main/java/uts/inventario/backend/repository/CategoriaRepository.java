package uts.inventario.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uts.inventario.backend.entity.Categoria;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long> { }

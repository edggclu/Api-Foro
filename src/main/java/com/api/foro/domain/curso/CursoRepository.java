package com.api.foro.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("select c from Curso c where c.nombre = :nombre and c.categoria = :categoria")
    Curso getReferenceByNombreAndCategoria(String nombre, String categoria);
}

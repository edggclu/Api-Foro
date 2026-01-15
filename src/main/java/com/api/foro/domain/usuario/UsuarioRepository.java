package com.api.foro.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombre(String nombre);

    Usuario findByCorreo(String correo);

    boolean existsByCorreo(String correo);
    boolean existsByNombre(String nombre);

    Usuario findByNombreAndPassword(String nombre, String password);
}

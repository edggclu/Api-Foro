package com.api.foro.service;

import com.api.foro.domain.usuario.DatosDetalleUsuario;
import com.api.foro.domain.usuario.DatosRegistroUsuario;
import com.api.foro.domain.usuario.Usuario;
import com.api.foro.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public DatosDetalleUsuario registrarUsuario(DatosRegistroUsuario datos){
        if(repository.existsByNombre(datos.usuario())){
            throw new RuntimeException("Este usuario ya existe");
        }
        if(repository.existsByCorreo(datos.correo())){
            throw new RuntimeException("Ya existe una cuenta vinculada a este correo");
        }
        if (!datos.contrasena().equals(datos.confirmar_contrasena())){
            throw new RuntimeException("La contrasenas no coinciden");
        }

        String password = passwordEncoder.encode(datos.contrasena());
        Usuario usuario = new Usuario(null, datos.usuario(), datos.correo(), password);
        repository.save(usuario);
        return new DatosDetalleUsuario(usuario, datos.contrasena());
    }
}

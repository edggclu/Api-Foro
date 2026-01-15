package com.api.foro.domain.service;

import com.api.foro.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        var u = repository.findByNombre(username)
                .orElseThrow(() -> {
                            return new UsernameNotFoundException("Usuario no encontrado: " + username);
                        }
                );
        System.out.println(u.getPassword());
        return u;
    }
}

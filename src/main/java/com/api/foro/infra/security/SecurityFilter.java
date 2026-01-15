package com.api.foro.infra.security;

import com.api.foro.domain.usuario.Usuario;
import com.api.foro.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    private Usuario usuarioLogeado;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            usuarioRepository.findByNombre(subject).ifPresent(u -> {
                System.out.println(u.getNombre());
                var authentication = new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                usuarioLogeado = u;
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
//            System.out.println("Usuario Logeado: " + usuario);
        }
        filterChain.doFilter(request, response);

    }

    private String recuperarToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if (authorization != null) {
            return authorization.replace("Bearer ", "");
        } else {
            return null;
        }
    }

    public Usuario getUser() {
        return usuarioLogeado;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/login");
    }
}

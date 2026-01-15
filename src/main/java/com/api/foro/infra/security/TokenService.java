package com.api.foro.infra.security;

import com.api.foro.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${foro.security.token.secret}")
    private String secret;

    public String generarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Foro")
                    .withSubject(usuario.getNombre())
                    .withExpiresAt(fechaExpiracion())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar Token", exception);
        }
    }

    private Instant fechaExpiracion() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-05:00"));
    }

    public String getSubject(String token) {
        try {
            var algorimo = Algorithm.HMAC256(secret);
            return JWT.require(algorimo)
                    .withIssuer("API Foro")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            throw new RuntimeException("Token JWT invalido o expirado");
        }
    }
}

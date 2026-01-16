package com.api.foro.service;

import com.api.foro.domain.respuesta.*;
import com.api.foro.domain.topico.DatosDetalleTopico;
import com.api.foro.domain.topico.Topico;
import com.api.foro.domain.topico.TopicoRepository;
import com.api.foro.domain.usuario.Usuario;
import com.api.foro.domain.usuario.UsuarioRepository;
import com.api.foro.infra.exceptions.UsuarioNoPermitidoException;
import com.api.foro.infra.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class RespuestaService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity publicar(Long id, DatosPublicarRespuesta datos){
        Topico topico =  topicoRepository.getReferenceById(id);
        Respuesta respuesta = new Respuesta(
                null, datos.mensaje(),
                topico, LocalDateTime.now(),
                securityFilter.getUser(),
                false);
        topico.getRespuesta().add(respuesta);
        respuestaRepository.save(respuesta);
        return ResponseEntity.ok().body(new DatosDetalleTopico(topico));
    }
    public void delete(Long id) {
        var respuesta = respuestaRepository.getReferenceById(id);
        Usuario thisUsuario = securityFilter.getUser();
        if(respuesta.getAutor()!=thisUsuario){
            throw new UsuarioNoPermitidoException("No tiene permitido borrar esta respuesta");
        }
        respuestaRepository.delete(respuesta);

    }

    public ActualizarDetalleRespuesta actualizar(Long id, DatosActualizarRespuesta datos) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        if(!esAutor(respuesta)){
            throw new UsuarioNoPermitidoException("No tiene permitido actualizar esta respuesta");
        }
        respuesta.setMensaje(datos.mensaje());
        return new ActualizarDetalleRespuesta(respuesta);
    }
    private Boolean esAutor(Respuesta respuesta){
        Usuario autorTopico = usuarioRepository.getReferenceById(respuesta.getAutor().getId());
        return Objects.equals(autorTopico.getNombre(), securityFilter.getUser().getNombre());
    }
}

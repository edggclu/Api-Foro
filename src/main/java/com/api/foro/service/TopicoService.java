package com.api.foro.service;

import com.api.foro.domain.curso.Curso;
import com.api.foro.domain.curso.CursoRepository;
import com.api.foro.domain.respuesta.RespuestaRepository;
import com.api.foro.domain.topico.*;
import com.api.foro.domain.usuario.Usuario;
import com.api.foro.domain.usuario.UsuarioRepository;
import com.api.foro.infra.exceptions.UsuarioNoPermitidoException;
import com.api.foro.infra.security.SecurityFilter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private SecurityFilter securityFilter;
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity publicar(DatosPublicarTopico datos) {
        if (topicoRepository.existsTopicoByTitulo(datos.titulo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            Usuario autor = securityFilter.getUser();
            var curso = cursoRepository.getReferenceByNombreAndCategoria(datos.curso().nombre(), datos.curso().categoria());
            if (curso == null) {
                curso = new Curso(datos.curso());
            }
            var topico = new Topico(null,
                    datos.titulo(),
                    datos.mensaje(),
                    LocalDate.now(),
                    StatusTopico.NO_SOLUCIONADO,
                    autor,
                    curso,
                    null
            );
            topicoRepository.save(topico);
            return ResponseEntity.status(HttpStatus.CREATED).body(new DatosDetalleTopico(topico));
        }
    }

    public ResponseEntity convertirRespuestaEnSolucion(Long idRespuesta, DatosRespuestaSolucion datos) {
        var respuesta = respuestaRepository.getReferenceById(idRespuesta);
        var topico = topicoRepository.getReferenceById(respuesta.getTopico().getId());

        if (esAutorDelTopico(topico)) {
            respuesta.cambiarSolucion(datos.solucion());
            if (datos.solucion()) {
                topico.cambiarEstatusTopico(StatusTopico.SOLUCIONADO);
            } else {
                topico.cambiarEstatusTopico(StatusTopico.NO_SOLUCIONADO);
            }
            return ResponseEntity.ok(new DatosDetalleTopico(topico));
        } else {
            throw new UsuarioNoPermitidoException("Solo el autor del topico puede seleccionar la respuesta");
        }
    }
    public ResponseEntity actualizarTopico(Long id, DatosActualizarTopico datos){
        var topico =  topicoRepository.getReferenceById(id);
        if (esAutorDelTopico(topico)) {
            topico.setMensaje(datos.mensaje());
            return  ResponseEntity.status(HttpStatus.CREATED).body(new DatosDetalleTopico(topico));
        }
        else {
            throw new UsuarioNoPermitidoException("Solo el autor del topico puede actualizar el topico");
        }
    }


    public ResponseEntity eliminarTopico(Long id) {
        var topico = topicoRepository.getReferenceById(id);
        if (!esAutorDelTopico(topico)) {
            throw new UsuarioNoPermitidoException("Solo el autor del topico puede eliminar");
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    private Boolean esAutorDelTopico(Topico topico){
        Usuario autorTopico = usuarioRepository.getReferenceById(topico.getAutor().getId());
        return Objects.equals(autorTopico.getNombre(), securityFilter.getUser().getNombre());
    }
}

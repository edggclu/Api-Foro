package com.api.foro.domain.service;

import com.api.foro.domain.curso.Curso;
import com.api.foro.domain.curso.CursoRepository;
import com.api.foro.domain.topico.DatosPublicarTopico;
import com.api.foro.domain.topico.StatusTopico;
import com.api.foro.domain.topico.Topico;
import com.api.foro.domain.topico.TopicoRepository;
import com.api.foro.domain.usuario.Usuario;
import com.api.foro.infra.security.SecurityFilter;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private SecurityFilter securityFilter;

    public ResponseEntity publicar(DatosPublicarTopico datos) {
        if(topicoRepository.existsTopicoByTitulo(datos.titulo())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else{
            Usuario autor = securityFilter.getUser();
            var curso = cursoRepository.getReferenceByNombreAndCategoria(datos.curso().nombre(), datos.curso().categoria());
            if(curso==null){curso = new Curso(datos.curso());}
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
            return ResponseEntity.status(HttpStatus.CREATED).body(topico);
        }
    }
}

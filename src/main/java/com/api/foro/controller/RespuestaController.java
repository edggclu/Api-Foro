package com.api.foro.controller;

import com.api.foro.domain.respuesta.DatosActualizarRespuesta;
import com.api.foro.domain.respuesta.DatosPublicarRespuesta;
import com.api.foro.service.RespuestaService;
import com.api.foro.domain.topico.DatosDetalleTopico;
import com.api.foro.domain.topico.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos/{idTopico}/respuestas")
public class RespuestaController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespuestaService respuestaService;

    @GetMapping
    public ResponseEntity listarRespuestas(@PathVariable Long idTopico){
        var response = topicoRepository.findById(idTopico).stream().map(DatosDetalleTopico::new).toList();
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    public ResponseEntity publicarRespuesta(@PathVariable Long idTopico, @RequestBody @Valid DatosPublicarRespuesta respuesta) {
        return respuestaService.publicar(idTopico, respuesta);
    }

    @DeleteMapping("/{id}")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        respuestaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity delete(@PathVariable Long id, @RequestBody @Valid DatosActualizarRespuesta datos) {
        var response = respuestaService.actualizar(id, datos);
        return ResponseEntity.ok(response);
    }
}

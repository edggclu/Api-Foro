package com.api.foro.controller;

import com.api.foro.domain.topico.*;
import com.api.foro.service.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;
    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public ResponseEntity<Page<DatosListarTopicos>> listarTopicos(@PageableDefault(size = 10, sort = {"fechaCreacion"}, direction = Sort.Direction.ASC) Pageable paginacion) {
        var response = topicoRepository.findAll(paginacion).map(DatosListarTopicos::new);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity detallarTopico(@PathVariable Long id) {
        var response = topicoRepository.findById(id).get();
        var topico = new DatosDetalleTopico(response);
        return ResponseEntity.ok().body(topico);
    }

    @Transactional
    @PostMapping
    public ResponseEntity publicarTopico(@RequestBody @Valid DatosPublicarTopico datos) {
        return topicoService.publicar(datos);
    }

    @Transactional
    @PutMapping("/{id}/respuestas/{idRespuesta}")
    public ResponseEntity seleccionarSolucion(@PathVariable Long id, @PathVariable Long idRespuesta, @RequestBody @Valid DatosRespuestaSolucion datos) {
        return topicoService.convertirRespuestaEnSolucion(idRespuesta, datos);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datos) {
        return topicoService.actualizarTopico(id, datos);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        return topicoService.eliminarTopico(id);
    }
}

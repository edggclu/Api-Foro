package com.api.foro.controller;

import com.api.foro.domain.topico.DatosPublicarTopico;
import com.api.foro.domain.service.TopicoService;
import com.api.foro.domain.topico.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity ok() {
        var response = topicoRepository.findAll();
        return ResponseEntity.ok().body(response);
    }

    @Transactional
    @PostMapping
    public ResponseEntity publicarTopico(@RequestBody @Valid DatosPublicarTopico datos) {
        return topicoService.publicar(datos);
    }
}

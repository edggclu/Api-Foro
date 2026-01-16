package com.api.foro.domain.topico;

import com.api.foro.domain.curso.DatosDetalleCurso;
import com.api.foro.domain.respuesta.DatosDetalleRespuesta;

import java.time.LocalDate;
import java.util.List;

public record DatosDetalleTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        StatusTopico status,
        LocalDate fecha_creacion,
        DatosDetalleCurso curso,
        List<DatosDetalleRespuesta> respuestas) {
    public DatosDetalleTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getAutor().getUsername(),
                topico.getStatus(),topico.getFechaCreacion(), new DatosDetalleCurso(topico.getCurso()),
                topico.getRespuesta().stream().map(DatosDetalleRespuesta::new).toList());

    }
}

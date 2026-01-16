package com.api.foro.domain.respuesta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime fecha_creacion,
        String autor,
        Boolean solucion
        ) {
    public DatosDetalleRespuesta(Respuesta respuesta){
        this(respuesta.getId(),
                respuesta.getMensaje(), respuesta.getFecha_creacion(),
                respuesta.getAutor().getNombre(), respuesta.getSolucion());
    }
}

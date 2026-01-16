package com.api.foro.domain.respuesta;

public record ActualizarDetalleRespuesta(String topico,Long idRespuesta, String nuevoMensaje) {

    public ActualizarDetalleRespuesta(Respuesta respuesta){
        this(respuesta.getTopico().getTitulo(), respuesta.getId(), respuesta.getMensaje());
    }
}

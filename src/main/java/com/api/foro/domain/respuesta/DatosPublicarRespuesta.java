package com.api.foro.domain.respuesta;

import jakarta.validation.constraints.NotBlank;

public record DatosPublicarRespuesta(
        @NotBlank String mensaje) {
}

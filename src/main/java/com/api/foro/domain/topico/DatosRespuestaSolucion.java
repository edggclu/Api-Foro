package com.api.foro.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRespuestaSolucion(
        @NotNull
        Boolean solucion
) {
}

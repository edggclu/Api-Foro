package com.api.foro.domain.topico;

import com.api.foro.domain.curso.DatosNuevoCurso;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record DatosPublicarTopico(
        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        @Valid
        DatosNuevoCurso curso

) {
}

package com.api.foro.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record DatosNuevoCurso(
        @NotBlank
        String nombre,

        @NotBlank
        String categoria
) {
}

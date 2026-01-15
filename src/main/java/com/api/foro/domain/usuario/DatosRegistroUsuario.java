package com.api.foro.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuario(

        @NotBlank
        String usuario,

        @NotBlank
        @Email
        String correo,

        @NotBlank
        String contrasena,

        @NotBlank
        String confirmar_contrasena
) {

}

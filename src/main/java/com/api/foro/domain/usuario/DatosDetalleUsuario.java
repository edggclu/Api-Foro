package com.api.foro.domain.usuario;

public record DatosDetalleUsuario(Long id, String nombre, String correo, String contrasena) {
    public DatosDetalleUsuario(Usuario usuario, String nonEncryptedPassword){
        this(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), nonEncryptedPassword);
    }
}

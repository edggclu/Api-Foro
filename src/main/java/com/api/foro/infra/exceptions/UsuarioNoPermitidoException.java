package com.api.foro.infra.exceptions;

public class UsuarioNoPermitidoException extends RuntimeException {
    public UsuarioNoPermitidoException(String s){
        super(s);
    }
}

package com.api.foro.infra.exceptions;

public class PostExistenteException extends RuntimeException{
    public PostExistenteException(String s){
        super(s);
    }
}

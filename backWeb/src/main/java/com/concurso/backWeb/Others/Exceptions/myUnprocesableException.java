package com.concurso.backWeb.Others.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class myUnprocesableException extends RuntimeException{
    public myUnprocesableException(String message){
        super(message);
    }
}

package com.plazoleta.usuarios.dominio.exception;

public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException() {
        super("Credenciales invalidas");
    }
}

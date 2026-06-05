/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.application.exception;

import java.util.List;

/**
 *
 * @author Usuario
 */
public class ErrorResponse {
    private String mensaje;
    private List<String> errores;

    public ErrorResponse() {}

    public ErrorResponse(String mensaje, List<String> errores) {
        this.mensaje = mensaje;
        this.errores = errores;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }
}

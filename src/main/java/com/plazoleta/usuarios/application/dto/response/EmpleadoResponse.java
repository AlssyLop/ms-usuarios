package com.plazoleta.usuarios.application.dto.response;

public class EmpleadoResponse {

    private String mensaje;

    public EmpleadoResponse() {}

    public EmpleadoResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}

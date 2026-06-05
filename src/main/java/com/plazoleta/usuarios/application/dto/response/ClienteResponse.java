package com.plazoleta.usuarios.application.dto.response;

public class ClienteResponse {
    private String mensaje;

    public ClienteResponse() {}

    public ClienteResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}

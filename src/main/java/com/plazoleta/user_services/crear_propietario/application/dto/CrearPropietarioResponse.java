package com.plazoleta.user_services.crear_propietario.application.dto;

public class CrearPropietarioResponse {

    private String mensaje;
    private Long id;
    private String nombre;
    private String apellido;
    private String documentoDeIdentidad;
    private String celular;
    private String correo;
    private String rol;

    public CrearPropietarioResponse() {}

    public CrearPropietarioResponse(String mensaje, Long id, String nombre, String apellido,
                                    String documentoDeIdentidad, String celular, String correo, String rol) {
        this.mensaje = mensaje;
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documentoDeIdentidad = documentoDeIdentidad;
        this.celular = celular;
        this.correo = correo;
        this.rol = rol;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDocumentoDeIdentidad() { return documentoDeIdentidad; }
    public void setDocumentoDeIdentidad(String documentoDeIdentidad) { this.documentoDeIdentidad = documentoDeIdentidad; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}

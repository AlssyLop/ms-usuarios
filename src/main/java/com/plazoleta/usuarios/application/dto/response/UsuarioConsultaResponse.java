package com.plazoleta.usuarios.application.dto.response;

public class UsuarioConsultaResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String rol;

    public UsuarioConsultaResponse() {}

    public UsuarioConsultaResponse(Long id, String nombre, String apellido, String correo, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}

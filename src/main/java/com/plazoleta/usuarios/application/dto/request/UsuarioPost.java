package com.plazoleta.usuarios.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioPost {

    @Schema(description = "Nombre del usuario", example = "Alcibiades")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Lopez")
    private String apellido;

    @Schema(description = "Documento de identidad (solo numerico)", example = "12345678")
    private String documentoDeIdentidad;

    @Schema(description = "Celular con formato internacional", example = "+573054433893")
    private String celular;

    @Schema(description = "Fecha de nacimiento en formato dd/MM/yyyy", example = "21/05/2004")
    private String fechaNacimiento;

    @Schema(description = "Correo electronico", example = "alcibiades@gmail.com")
    private String correo;

    @Schema(description = "Clave (minimo 8 caracteres)", example = "12345678")
    private String clave;

    public UsuarioPost() {}

    public UsuarioPost(String nombre, String apellido, String documentoDeIdentidad,
                       String celular, String fechaNacimiento, String correo, String clave) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documentoDeIdentidad = documentoDeIdentidad;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.clave = clave;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDocumentoDeIdentidad() { return documentoDeIdentidad; }
    public void setDocumentoDeIdentidad(String documentoDeIdentidad) { this.documentoDeIdentidad = documentoDeIdentidad; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}

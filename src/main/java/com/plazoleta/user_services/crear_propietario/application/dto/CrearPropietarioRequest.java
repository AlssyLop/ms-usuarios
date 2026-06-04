package com.plazoleta.user_services.crear_propietario.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CrearPropietarioRequest {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El apellido es requerido")
    private String apellido;

    @NotBlank(message = "El documento de identidad es requerido")
    private String documentoDeIdentidad;

    @NotBlank(message = "El celular es requerido")
    @Pattern(regexp = "^\\+\\d{1,12}$", message = "El celular debe comenzar con '+' y tener maximo 13 caracteres")
    private String celular;

    @NotBlank(message = "La fecha de nacimiento es requerida")
    private String fechaNacimiento;

    @NotBlank(message = "El correo es requerido")
    @Email(message = "El correo no tiene un formato valido")
    private String correo;

    @NotBlank(message = "La clave es requerida")
    @Size(min = 8, message = "La clave debe tener minimo 8 caracteres")
    private String clave;

    public CrearPropietarioRequest() {}

    public CrearPropietarioRequest(String nombre, String apellido, String documentoDeIdentidad,
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.application.dto.request;

import com.plazoleta.usuarios.dominio.modelo.value.Apellido;
import com.plazoleta.usuarios.dominio.modelo.value.Celular;
import com.plazoleta.usuarios.dominio.modelo.value.Clave;
import com.plazoleta.usuarios.dominio.modelo.value.Correo;
import com.plazoleta.usuarios.dominio.modelo.value.Documento;
import com.plazoleta.usuarios.dominio.modelo.value.FechaNacimiento;
import com.plazoleta.usuarios.dominio.modelo.value.Nombre;

/**
 *
 * @author Usuario
 */
public class UsuarioPost {
    
    private Nombre nombre;

    private Apellido apellido;

    private Documento documentoDeIdentidad;

    private Celular celular;

    private FechaNacimiento fechaNacimiento;

    private Correo correo;

    private Clave clave;

    public UsuarioPost() {}

    public UsuarioPost(Nombre nombre, Apellido apellido, Documento documentoDeIdentidad,
                                   Celular celular, FechaNacimiento fechaNacimiento, Correo correo, Clave clave) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documentoDeIdentidad = documentoDeIdentidad;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.clave = clave;
    }

    public Nombre getNombre() {
        return nombre;
    }

    public void setNombre(Nombre nombre) {
        this.nombre = nombre;
    }

    public Apellido getApellido() {
        return apellido;
    }

    public void setApellido(Apellido apellido) {
        this.apellido = apellido;
    }

    public Documento getDocumentoDeIdentidad() {
        return documentoDeIdentidad;
    }

    public void setDocumentoDeIdentidad(Documento documentoDeIdentidad) {
        this.documentoDeIdentidad = documentoDeIdentidad;
    }

    public Celular getCelular() {
        return celular;
    }

    public void setCelular(Celular celular) {
        this.celular = celular;
    }

    public FechaNacimiento getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(FechaNacimiento fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Correo getCorreo() {
        return correo;
    }

    public void setCorreo(Correo correo) {
        this.correo = correo;
    }

    public Clave getClave() {
        return clave;
    }

    public void setClave(Clave clave) {
        this.clave = clave;
    }

    
}

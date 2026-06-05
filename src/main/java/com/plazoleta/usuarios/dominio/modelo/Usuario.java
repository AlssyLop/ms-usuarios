/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.dominio.modelo;

import com.plazoleta.usuarios.dominio.modelo.value.TipoRol;
import java.time.LocalDate;
import com.plazoleta.usuarios.dominio.modelo.value.Apellido;
import com.plazoleta.usuarios.dominio.modelo.value.Celular;
import com.plazoleta.usuarios.dominio.modelo.value.Clave;
import com.plazoleta.usuarios.dominio.modelo.value.Correo;
import com.plazoleta.usuarios.dominio.modelo.value.Documento;
import com.plazoleta.usuarios.dominio.modelo.value.FechaNacimiento;
import com.plazoleta.usuarios.dominio.modelo.value.Nombre;

public class Usuario {
    private Long id;
    private Nombre nombre;
    private Apellido apellido;
    private Documento documentoDeIdentidad;
    private Celular celular;
    private FechaNacimiento fechaNacimiento;
    private Correo correo;
    private Clave clave;
    private TipoRol rol;
    private boolean activo;
    
    public Usuario() {}

    public Usuario(Long id, Nombre nombre, Apellido apellido, Documento documentoDeIdentidad,
                   Celular celular, FechaNacimiento fechaNacimiento, Correo correo,
                   Clave clave, TipoRol rol, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documentoDeIdentidad = documentoDeIdentidad;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
        this.activo = activo;
    }

    public Long getId() {
        return id;
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

    public TipoRol getRol() {
        return rol;
    }

    public void setRol(TipoRol rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
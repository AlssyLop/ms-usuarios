/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.dominio.modelo.value;

/**
 *
 * @author Usuario
 */
public class Clave {
    private String valor;
    private static final int CLAVE_MIN_LENGTH = 8;

    public Clave(String valor) {
        this.valor = valor;
        this.validarContraseña();
    }
    
    private void validarContraseña(){    
        if (valor == null || valor.length() < CLAVE_MIN_LENGTH) {
            throw new IllegalArgumentException("La clave debe tener minimo " + CLAVE_MIN_LENGTH + " caracteres");
        }
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}

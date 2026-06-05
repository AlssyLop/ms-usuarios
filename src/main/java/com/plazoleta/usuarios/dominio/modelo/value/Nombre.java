/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.dominio.modelo.value;

import java.util.regex.Pattern;

/**
 *
 * @author Usuario
 */
public class Nombre {
    private final String valor;
    private static final Pattern VALIDO = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]{2,100}$");

    public Nombre(String valor) {
        this.valor = valor;
        this.validarNombreUsuario();
    }
    
    private void validarNombreUsuario(){
        if (valor == null || valor.length() == 0) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        
        if (valor.length() > 100 || !VALIDO.matcher(valor).matches()){
            throw new IllegalArgumentException("Nombre inválido");
        }
    }

    public String getValor() {
        return valor.toUpperCase();
    }
}

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
public class Celular {
    private final String valor;
    private static final Pattern CELULAR = Pattern.compile("^\\+[0-9]{1,12}$");

    public Celular(String valor) {
        this.valor = valor;
        this.validarCelular();
    }
    
    private void validarCelular(){
        if (valor == null || valor.length() == 0) {
            throw new IllegalArgumentException("El número de celular es requerido");
        }
        
        String celular = valor.trim();
        if (valor.length() > 13 || !CELULAR.matcher(celular).matches()){
            throw new IllegalArgumentException("Número de celular es inválido. Debe comenzar con '+', contener solo números y tener un máximo de 13 caracteres.");
        }
    }

    public String getValor() {
        return valor;
    }
}

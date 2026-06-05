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
public class Correo {
    private final String valor;
    private static final Pattern EMAIl = Pattern.compile("^[a-z0-9_\\.\\-]+@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,100}$");

    public Correo(String valor) {
        this.valor = valor;
        this.validarCorreo();
    }
    
    private void validarCorreo(){
        if (valor == null || valor.length() == 0) {
            throw new IllegalArgumentException("El correo es requerido");
        }
        
        String correo = valor.trim();
        if (valor.length() > 100 || !EMAIl.matcher(correo).matches()){
            throw new IllegalArgumentException("Correo inválido");
        }
    }

    public String getValor() {
        return valor;
    }
    
    
}

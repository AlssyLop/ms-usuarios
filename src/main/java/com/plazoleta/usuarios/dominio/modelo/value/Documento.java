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
public class Documento {
    private final String valor;
    private static final Pattern DOCUMENTO = Pattern.compile("^[0-9]{5,20}$");

    public Documento(String valor) {
        this.valor = valor;
        this.validarDocumento();
    }
    
    private void validarDocumento(){
        if (valor == null || valor.length() == 0) {
            throw new IllegalArgumentException("El documento de identidad es requerido");
        }
        
        String doc = valor.trim();
        if (valor.length() > 20 || !DOCUMENTO.matcher(doc).matches()){
            throw new IllegalArgumentException("Documento inválido. Debe contener solo números");
        }
    }

    public String getValor() {
        return valor;
    }
    
}

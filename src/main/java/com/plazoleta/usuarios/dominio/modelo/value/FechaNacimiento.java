/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.dominio.modelo.value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 *
 * @author Usuario
 */
public class FechaNacimiento {
    private final String valor;
    private final LocalDate fecha;
    private static final DateTimeFormatter FORMATEADOR = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public FechaNacimiento(String valor) {
        this.valor = valor;
        this.fecha = this.validarFechaNacimiento();
    }
    
    private LocalDate validarFechaNacimiento(){
        if (valor == null || valor.length() == 0) {
            throw new IllegalArgumentException("La fecha de nacimiento es requerido");
        }
        
        try {
            return LocalDate.parse(valor.trim(), FORMATEADOR);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha es inválida. Debe tener el formato dd/MM/yyyy.");
        }
    }

    public LocalDate getValor() {
        return fecha;
    }
}

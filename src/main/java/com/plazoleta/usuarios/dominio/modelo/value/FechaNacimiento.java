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
    private static final DateTimeFormatter FORMATEADOR_ISO = DateTimeFormatter
            .ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);

    public FechaNacimiento(String valor) {
        this.valor = valor;
        this.fecha = this.validarFechaNacimiento();
    }

    private LocalDate validarFechaNacimiento(){
        if (valor == null || valor.isEmpty()) {
            throw new IllegalArgumentException("La fecha de nacimiento es requerida");
        }

        String trimmed = valor.trim();
        try {
            return LocalDate.parse(trimmed, FORMATEADOR);
        } catch (DateTimeParseException e1) {
            try {
                return LocalDate.parse(trimmed, FORMATEADOR_ISO);
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("La fecha es invalida. Use dd/MM/yyyy o yyyy-MM-dd.");
            }
        }
    }

    public LocalDate getValor() {
        return fecha;
    }
}

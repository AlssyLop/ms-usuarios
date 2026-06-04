package com.plazoleta.user_services.crear_propietario.domain.usecase;

import com.plazoleta.user_services.crear_propietario.domain.api.ICrearPropietarioUseCase;
import com.plazoleta.user_services.common.domain.model.Usuario;
import com.plazoleta.user_services.common.domain.spi.IUsuarioRepositoryPort;

import java.time.LocalDate;
import java.time.Period;

public class CrearPropietarioUseCase implements ICrearPropietarioUseCase {

    private static final int EDAD_MINIMA = 18;
    private static final int TELEFONO_MAX_LENGTH = 13;
    private static final int CLAVE_MIN_LENGTH = 8;

    private final IUsuarioRepositoryPort usuarioRepository;

    public CrearPropietarioUseCase(IUsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario crearPropietario(Usuario usuario) {
        validarDocumento(usuario.getDocumentoDeIdentidad());
        validarCelular(usuario.getCelular());
        validarCorreo(usuario.getCorreo());
        validarClave(usuario.getClave());
        validarEdad(usuario.getFechaNacimiento());

        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo electronico ya esta registrado");
        }
        if (usuarioRepository.existsByDocumentoDeIdentidad(usuario.getDocumentoDeIdentidad())) {
            throw new IllegalArgumentException("El Documento de Identidad ya esta registrado");
        }

        return usuarioRepository.save(usuario);
    }

    private void validarDocumento(String documento) {
        if (documento == null || !documento.matches("\\d+")) {
            throw new IllegalArgumentException("El documento de identidad debe ser numerico");
        }
    }

    private void validarCelular(String celular) {
        if (celular == null || !celular.matches("^\\+\\d{1,12}$")) {
            throw new IllegalArgumentException("El celular debe comenzar con '+' y tener maximo 13 caracteres");
        }
    }

    private void validarCorreo(String correo) {
        if (correo == null || !correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("El correo no tiene un formato valido");
        }
    }

    private void validarClave(String clave) {
        if (clave == null || clave.length() < CLAVE_MIN_LENGTH) {
            throw new IllegalArgumentException("La clave debe tener minimo " + CLAVE_MIN_LENGTH + " caracteres");
        }
    }

    private void validarEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria");
        }
        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edad < EDAD_MINIMA) {
            throw new IllegalArgumentException("El usuario debe ser mayor de edad (>= 18 anos)");
        }
    }
}

package com.plazoleta.usuarios.dominio.usecase;

import com.plazoleta.usuarios.dominio.exception.CredencialesInvalidasException;
import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.dominio.modelo.value.Correo;
import com.plazoleta.usuarios.dominio.spi.AutenticarUsuarioPort;
import com.plazoleta.usuarios.dominio.spi.UsuarioRespositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AutenticarUsuario implements AutenticarUsuarioPort {

    private final UsuarioRespositoryPort usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AutenticarUsuario(UsuarioRespositoryPort usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario autenticar(Correo correo, String claveIngresada) {
        Usuario usuario = usuarioRepository.obtenerPorCorreo(correo)
                .orElseThrow(CredencialesInvalidasException::new);

        if (!passwordEncoder.matches(claveIngresada, usuario.getClave().getValor())) {
            throw new CredencialesInvalidasException();
        }

        return usuario;
    }
}

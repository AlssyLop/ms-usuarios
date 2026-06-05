package com.plazoleta.usuarios.application.handle;

import com.plazoleta.usuarios.application.dto.request.LoginRequest;
import com.plazoleta.usuarios.application.dto.response.LoginResponse;
import com.plazoleta.usuarios.dominio.exception.CredencialesInvalidasException;
import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.dominio.modelo.value.Correo;
import com.plazoleta.usuarios.dominio.spi.AutenticarUsuarioPort;
import com.plazoleta.usuarios.infrastructure.security.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class AutenticarHandle {

    private final AutenticarUsuarioPort autenticarUsuarioPort;
    private final JwtTokenProvider jwtTokenProvider;

    public AutenticarHandle(AutenticarUsuarioPort autenticarUsuarioPort,
                            JwtTokenProvider jwtTokenProvider) {
        this.autenticarUsuarioPort = autenticarUsuarioPort;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(LoginRequest request) {
        if (request.getCorreo() == null || request.getCorreo().isBlank()) {
            throw new CredencialesInvalidasException();
        }
        if (request.getClave() == null || request.getClave().isBlank()) {
            throw new CredencialesInvalidasException();
        }

        Correo correo = new Correo(request.getCorreo());
        Usuario usuario = autenticarUsuarioPort.autenticar(correo, request.getClave());
        String token = jwtTokenProvider.generarToken(usuario.getId(), usuario.getRol());
        return new LoginResponse(token);
    }
}

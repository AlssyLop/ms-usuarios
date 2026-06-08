package com.plazoleta.usuarios.application.handle;

import com.plazoleta.usuarios.application.dto.response.UsuarioConsultaResponse;
import com.plazoleta.usuarios.dominio.api.ConsultarUsuarioPort;
import com.plazoleta.usuarios.dominio.modelo.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ConsultarUsuarioHandle {

    private final ConsultarUsuarioPort consultarUsuario;

    public ConsultarUsuarioHandle(ConsultarUsuarioPort consultarUsuario) {
        this.consultarUsuario = consultarUsuario;
    }

    public UsuarioConsultaResponse consultarPorId(Long id) {
        Usuario usuario = consultarUsuario.consultarPorId(id);
        return new UsuarioConsultaResponse(
                usuario.getId(),
                usuario.getNombre().getValor(),
                usuario.getApellido().getValor(),
                usuario.getCelular().getValor(),
                usuario.getCorreo().getValor(),
                usuario.getRol().name()
        );
    }
}

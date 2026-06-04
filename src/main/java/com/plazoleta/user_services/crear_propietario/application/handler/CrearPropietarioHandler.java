package com.plazoleta.user_services.crear_propietario.application.handler;

import com.plazoleta.user_services.crear_propietario.application.dto.CrearPropietarioRequest;
import com.plazoleta.user_services.crear_propietario.application.dto.CrearPropietarioResponse;
import com.plazoleta.user_services.crear_propietario.application.factory.UsuarioFactory;
import com.plazoleta.user_services.crear_propietario.domain.api.ICrearPropietarioUseCase;
import com.plazoleta.user_services.common.domain.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CrearPropietarioHandler {

    private final ICrearPropietarioUseCase crearPropietarioUseCase;
    private final UsuarioFactory usuarioFactory;
    private final PasswordEncoder passwordEncoder;

    public CrearPropietarioHandler(ICrearPropietarioUseCase crearPropietarioUseCase,
                                   UsuarioFactory usuarioFactory,
                                   PasswordEncoder passwordEncoder) {
        this.crearPropietarioUseCase = crearPropietarioUseCase;
        this.usuarioFactory = usuarioFactory;
        this.passwordEncoder = passwordEncoder;
    }

    public CrearPropietarioResponse handle(CrearPropietarioRequest request) {
        Usuario usuario = usuarioFactory.toDomain(request);
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));

        Usuario creado = crearPropietarioUseCase.crearPropietario(usuario);

        return new CrearPropietarioResponse(
                "Propietario creado exitosamente",
                creado.getId(),
                creado.getNombre(),
                creado.getApellido(),
                creado.getDocumentoDeIdentidad(),
                creado.getCelular(),
                creado.getCorreo(),
                creado.getRol().name()
        );
    }
}

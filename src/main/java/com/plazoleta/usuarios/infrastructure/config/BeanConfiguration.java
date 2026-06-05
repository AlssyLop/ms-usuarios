package com.plazoleta.usuarios.infrastructure.config;

import com.plazoleta.usuarios.dominio.api.CrearUsuarioPort;
import com.plazoleta.usuarios.dominio.spi.UsuarioRespositoryPort;
import com.plazoleta.usuarios.dominio.usecase.CrearUsuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public CrearUsuarioPort crearUsuarioPort(UsuarioRespositoryPort usuarioRepository) {
        return new CrearUsuario(usuarioRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.plazoleta.user_services.common.infrastructure.config;

import com.plazoleta.user_services.common.domain.spi.IUsuarioRepositoryPort;
import com.plazoleta.user_services.crear_propietario.domain.api.ICrearPropietarioUseCase;
import com.plazoleta.user_services.crear_propietario.domain.usecase.CrearPropietarioUseCase;
import com.plazoleta.user_services.crear_propietario.application.factory.UsuarioFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public ICrearPropietarioUseCase crearPropietarioUseCase(IUsuarioRepositoryPort usuarioRepository) {
        return new CrearPropietarioUseCase(usuarioRepository);
    }

    @Bean
    public UsuarioFactory usuarioFactory() {
        return new UsuarioFactory();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

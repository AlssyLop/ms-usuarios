package com.plazoleta.usuarios.infrastructure.config;

import com.plazoleta.usuarios.dominio.api.CrearCuentaClientePort;
import com.plazoleta.usuarios.dominio.api.CrearEmpleadoPort;
import com.plazoleta.usuarios.dominio.api.CrearUsuarioPort;
import com.plazoleta.usuarios.dominio.api.ConsultarUsuarioPort;
import com.plazoleta.usuarios.dominio.spi.AutenticarUsuarioPort;
import com.plazoleta.usuarios.dominio.spi.UsuarioRespositoryPort;
import com.plazoleta.usuarios.dominio.usecase.AutenticarUsuario;
import com.plazoleta.usuarios.dominio.usecase.CrearCuentaCliente;
import com.plazoleta.usuarios.dominio.usecase.CrearEmpleado;
import com.plazoleta.usuarios.dominio.usecase.CrearUsuario;
import com.plazoleta.usuarios.dominio.usecase.ConsultarUsuario;
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
    public CrearEmpleadoPort crearEmpleadoPort(UsuarioRespositoryPort usuarioRepository) {
        return new CrearEmpleado(usuarioRepository);
    }

    @Bean
    public CrearCuentaClientePort crearCuentaClientePort(UsuarioRespositoryPort usuarioRepository) {
        return new CrearCuentaCliente(usuarioRepository);
    }

    @Bean
    public ConsultarUsuarioPort consultarUsuarioPort(UsuarioRespositoryPort usuarioRepository) {
        return new ConsultarUsuario(usuarioRepository);
    }

    @Bean
    public AutenticarUsuarioPort autenticarUsuarioPort(UsuarioRespositoryPort usuarioRepository,
                                                       PasswordEncoder passwordEncoder) {
        return new AutenticarUsuario(usuarioRepository, passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.plazoleta.usuarios.infrastructure.security.jwt;

import com.plazoleta.usuarios.dominio.modelo.value.TipoRol;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long expirationAdmin;
    private final long expirationPropietario;
    private final long expirationEmpleado;
    private final long expirationCliente;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration.admin}") long expirationAdmin,
            @Value("${jwt.expiration.propietario}") long expirationPropietario,
            @Value("${jwt.expiration.empleado}") long expirationEmpleado,
            @Value("${jwt.expiration.cliente}") long expirationCliente) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationAdmin = expirationAdmin;
        this.expirationPropietario = expirationPropietario;
        this.expirationEmpleado = expirationEmpleado;
        this.expirationCliente = expirationCliente;
    }

    public String generarToken(Long idUsuario, TipoRol rol) {
        long expiracion = obtenerExpiracion(rol);
        Date ahora = new Date();
        Date fechaExpiracion = new Date(ahora.getTime() + expiracion);

        return Jwts.builder()
                .claim("idUsuario", idUsuario)
                .claim("rol", rol.name())
                .issuedAt(ahora)
                .expiration(fechaExpiracion)
                .signWith(secretKey)
                .compact();
    }

    public boolean validarToken(String token) {
        try {
            obtenerClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long obtenerIdUsuario(String token) {
        return obtenerClaims(token).get("idUsuario", Long.class);
    }

    public String obtenerRol(String token) {
        return obtenerClaims(token).get("rol", String.class);
    }

    public Date obtenerExpiracion(String token) {
        return obtenerClaims(token).getExpiration();
    }

    public Date obtenerFechaCreacion(String token) {
        return obtenerClaims(token).getIssuedAt();
    }

    private Claims obtenerClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private long obtenerExpiracion(TipoRol rol) {
        return switch (rol) {
            case ADMINISTRADOR -> expirationAdmin;
            case PROPIETARIO -> expirationPropietario;
            case EMPLEADO -> expirationEmpleado;
            case CLIENTE -> expirationCliente;
        };
    }
}

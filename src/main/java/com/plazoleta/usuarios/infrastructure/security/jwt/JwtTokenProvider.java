package com.plazoleta.usuarios.infrastructure.security.jwt;

import com.plazoleta.usuarios.dominio.modelo.value.TipoRol;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final long expirationAdmin;
    private final long expirationPropietario;
    private final long expirationEmpleado;
    private final long expirationCliente;

    public JwtTokenProvider(
            @Value("${jwt.private-key}") String privateKeyPem,
            @Value("${jwt.public-key}") String publicKeyPem,
            @Value("${jwt.expiration.admin}") long expirationAdmin,
            @Value("${jwt.expiration.propietario}") long expirationPropietario,
            @Value("${jwt.expiration.empleado}") long expirationEmpleado,
            @Value("${jwt.expiration.cliente}") long expirationCliente) throws Exception {
        this.privateKey = parsePrivateKey(privateKeyPem);
        this.publicKey = parsePublicKey(publicKeyPem);
        this.expirationAdmin = expirationAdmin;
        this.expirationPropietario = expirationPropietario;
        this.expirationEmpleado = expirationEmpleado;
        this.expirationCliente = expirationCliente;
    }

    private PrivateKey parsePrivateKey(String pem) throws Exception {
        String base64 = pem.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(base64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private PublicKey parsePublicKey(String pem) throws Exception {
        String base64 = pem.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(base64);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(keySpec);
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
                .signWith(privateKey)
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
                .verifyWith(publicKey)
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
CREATE DATABASE IF NOT EXISTS plazoleta_usuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE plazoleta_usuarios;

CREATE TABLE rol (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO rol (nombre) VALUES
    ('ADMINISTRADOR'),
    ('PROPIETARIO'),
    ('EMPLEADO'),
    ('CLIENTE');

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    documento_de_identidad VARCHAR(20) NOT NULL UNIQUE,
    celular VARCHAR(13) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    id_rol BIGINT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES rol(id)
) ENGINE=InnoDB;

-- NOTA: La validación de mayoría de edad (≥ 18) se realiza en la lógica de dominio
-- antes de persistir, no con CHECK constraint de MySQL.

-- NOTA: El administrador por defecto se crea vía data.sql en el arranque de Spring Boot
-- para que la contraseña se encripte con BCrypt correctamente.

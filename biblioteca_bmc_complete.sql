-- =====================================================
-- SCRIPT COMPLETO PARA BASE DE DATOS BIBLIOTECA_BMC
-- Sistema de Gestión de Biblioteca - Spring Boot
-- =====================================================

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS biblioteca_bmc 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE biblioteca_bmc;

-- =====================================================
-- ELIMINAR TABLAS EXISTENTES (en orden correcto)
-- =====================================================
DROP TABLE IF EXISTS historial_prestamos;
DROP TABLE IF EXISTS historial_bibliotecario;
DROP TABLE IF EXISTS pago;
DROP TABLE IF EXISTS prestamos;
DROP TABLE IF EXISTS libro_autor;
DROP TABLE IF EXISTS ejemplar;
DROP TABLE IF EXISTS libro;
DROP TABLE IF EXISTS perfil_opcion;
DROP TABLE IF EXISTS opciones;
DROP TABLE IF EXISTS perfiles;
DROP TABLE IF EXISTS rol;
DROP TABLE IF EXISTS permiso;
DROP TABLE IF EXISTS bibliotecario;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS autor;
DROP TABLE IF EXISTS editorial;
DROP TABLE IF EXISTS genero;
DROP TABLE IF EXISTS categoria;

-- =====================================================
-- CREAR TABLAS PRINCIPALES
-- =====================================================

-- Tabla: categoria
CREATE TABLE categoria (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: genero
CREATE TABLE genero (
    id_genero BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_genero VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: editorial
CREATE TABLE editorial (
    id_editorial BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_editorial VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: autor
CREATE TABLE autor (
    id_autor BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_autor VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: opciones (para menús del sistema)
CREATE TABLE opciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ruta VARCHAR(100) NOT NULL,
    icono VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: perfiles (roles de usuario)
CREATE TABLE perfiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    estado BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: perfil_opcion (relación muchos a muchos)
CREATE TABLE perfil_opcion (
    id_perfil BIGINT NOT NULL,
    id_opcion BIGINT NOT NULL,
    PRIMARY KEY (id_perfil, id_opcion),
    FOREIGN KEY (id_perfil) REFERENCES perfiles(id) ON DELETE CASCADE,
    FOREIGN KEY (id_opcion) REFERENCES opciones(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: usuarios
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    estado INT NOT NULL DEFAULT 1,
    id_perfil BIGINT,
    FOREIGN KEY (id_perfil) REFERENCES perfiles(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: bibliotecario
CREATE TABLE bibliotecario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_bibliotecario VARCHAR(100) NOT NULL,
    clave_bibliotecario VARCHAR(50) NOT NULL,
    id_perfil BIGINT,
    FOREIGN KEY (id_perfil) REFERENCES perfiles(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: libro
CREATE TABLE libro (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    id_editorial BIGINT,
    id_genero BIGINT,
    id_categoria BIGINT,
    estado VARCHAR(50),
    FOREIGN KEY (id_editorial) REFERENCES editorial(id_editorial),
    FOREIGN KEY (id_genero) REFERENCES genero(id_genero),
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: libro_autor (relación muchos a muchos)
CREATE TABLE libro_autor (
    id_libro_por_autor INT AUTO_INCREMENT PRIMARY KEY,
    id_autor BIGINT,
    id_libro INT,
    FOREIGN KEY (id_autor) REFERENCES autor(id_autor),
    FOREIGN KEY (id_libro) REFERENCES libro(id_libro)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: ejemplar
CREATE TABLE ejemplar (
    id_ejemplar INT AUTO_INCREMENT PRIMARY KEY,
    cantidad_ejemplar INT NOT NULL,
    localizacion VARCHAR(150) NOT NULL,
    id_libro INT NOT NULL,
    FOREIGN KEY (id_libro) REFERENCES libro(id_libro)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: prestamos
CREATE TABLE prestamos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE NOT NULL,
    id_usuarios BIGINT,
    id_ejemplar INT,
    FOREIGN KEY (id_usuarios) REFERENCES usuarios(id),
    FOREIGN KEY (id_ejemplar) REFERENCES ejemplar(id_ejemplar)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: pago
CREATE TABLE pago (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    multa DECIMAL(7,2) NOT NULL,
    fecha_pago DATETIME NOT NULL,
    estado INT NOT NULL,
    id_prestamo BIGINT,
    FOREIGN KEY (id_prestamo) REFERENCES prestamos(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: historial_prestamos
CREATE TABLE historial_prestamos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_prestamo BIGINT,
    id_ejemplar INT,
    id_usuario BIGINT,
    fecha_prestamo DATE NOT NULL,
    fecha_real_devolucion DATE NOT NULL,
    FOREIGN KEY (id_prestamo) REFERENCES prestamos(id),
    FOREIGN KEY (id_ejemplar) REFERENCES ejemplar(id_ejemplar),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: historial_bibliotecario
CREATE TABLE historial_bibliotecario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_bibliotecario BIGINT,
    fecha_acceso DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    hora_entrada TIME NOT NULL,
    hora_salida TIME NOT NULL,
    FOREIGN KEY (id_bibliotecario) REFERENCES bibliotecario(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tablas adicionales (si se necesitan)
CREATE TABLE rol (
    idRol BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombreRol VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE permiso (
    idPermiso BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombrePermiso VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- INSERTAR DATOS INICIALES
-- =====================================================

-- Insertar categorías
INSERT INTO categoria (nombre_categoria) VALUES 
('Ficción'),
('No Ficción'),
('Ciencia'),
('Historia'),
('Biografía'),
('Tecnología'),
('Arte'),
('Filosofía');

-- Insertar géneros
INSERT INTO genero (nombre_genero) VALUES 
('Romance'),
('Misterio'),
('Ciencia Ficción'),
('Fantasía'),
('Terror'),
('Aventura'),
('Drama'),
('Comedia'),
('Thriller'),
('Novela Histórica');

-- Insertar editoriales
INSERT INTO editorial (nombre_editorial) VALUES 
('Planeta'),
('Alfaguara'),
('Seix Barral'),
('Tusquets'),
('Anagrama'),
('Debolsillo'),
('Penguin Random House'),
('HarperCollins'),
('Grupo Editorial Norma'),
('Editorial SM');

-- Insertar autores
INSERT INTO autor (nombre_autor) VALUES 
('Rebeca Yarros'),
('Iryna Zubkova'),
('Mercedes Ron'),
('Joana Marcús'),
('Arturo Pérez-Reverte'),
('Freida McFadden'),
('R. F. Kuang'),
('Gabriel García Márquez'),
('Mario Vargas Llosa'),
('Isabel Allende');

-- Insertar opciones del menú
INSERT INTO opciones (nombre, ruta, icono) VALUES 
('Dashboard', '/admin/dashboard', 'bi-speedometer2'),
('Usuarios', '/usuarios', 'bi-people'),
('Libros', '/libros', 'bi-book'),
('Préstamos', '/prestamos', 'bi-journal-check'),
('Pagos', '/pagos', 'bi-credit-card'),
('Perfiles', '/perfiles', 'bi-person-badge'),
('Autores', '/autores', 'bi-person'),
('Editoriales', '/editoriales', 'bi-building'),
('Géneros', '/generos', 'bi-tags'),
('Categorías', '/categorias', 'bi-collection'),
('Ejemplares', '/ejemplares', 'bi-book-half'),
('Historial Préstamos', '/historial-prestamos', 'bi-clock-history'),
('Historial Bibliotecario', '/historial-bibliotecario', 'bi-person-workspace'),
('Inicio Usuario', '/usuario/inicio', 'bi-house');

-- Insertar perfiles
INSERT INTO perfiles (nombre, descripcion, estado) VALUES 
('ADMIN', 'Administrador del sistema con acceso completo', TRUE),
('BIBLIOTECARIO', 'Bibliotecario con acceso a gestión de préstamos y usuarios', TRUE),
('USUARIO', 'Usuario regular con acceso limitado', TRUE);

-- Asignar opciones a perfiles
-- ADMIN: todas las opciones
INSERT INTO perfil_opcion (id_perfil, id_opcion) 
SELECT 1, id FROM opciones;

-- BIBLIOTECARIO: opciones de gestión
INSERT INTO perfil_opcion (id_perfil, id_opcion) VALUES 
(2, 1), -- Dashboard
(2, 2), -- Usuarios
(2, 3), -- Libros
(2, 4), -- Préstamos
(2, 5), -- Pagos
(2, 7), -- Autores
(2, 8), -- Editoriales
(2, 9), -- Géneros
(2, 10), -- Categorías
(2, 11), -- Ejemplares
(2, 12), -- Historial Préstamos
(2, 13); -- Historial Bibliotecario

-- USUARIO: solo inicio
INSERT INTO perfil_opcion (id_perfil, id_opcion) VALUES 
(3, 14); -- Inicio Usuario

-- Insertar usuarios de prueba (contraseñas encriptadas con BCrypt)
-- Contraseña para todos: "123456"
INSERT INTO usuarios (nombre, usuario, clave, correo, estado, id_perfil) VALUES 
('Administrador Sistema', 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'admin@biblioteca.com', 1, 1),
('María García López', 'mgarcia', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'maria.garcia@email.com', 1, 2),
('Carlos Bibliotecario', 'cbibliotecario', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'carlos.biblio@email.com', 1, 2),
('Ana Usuario', 'ausuario', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ana.usuario@email.com', 1, 3),
('Luis Pérez', 'lperez', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'luis.perez@email.com', 1, 3);

-- Insertar bibliotecarios
INSERT INTO bibliotecario (nombre_bibliotecario, clave_bibliotecario, id_perfil) VALUES 
('Sistema Bibliotecario', 'biblio123', 2),
('Elena Bibliotecaria', 'elena123', 2);

-- Insertar libros de ejemplo
INSERT INTO libro (titulo, id_editorial, id_genero, id_categoria, estado) VALUES 
('Alas de sangre (Empíreo 1)', 1, 1, 1, 'Disponible'),
('Acelerando en rojo', 2, 2, 1, 'Disponible'),
('Marfil (Enfrentados)', 3, 1, 1, 'Disponible'),
('Antes de Diciembre', 4, 1, 1, 'Disponible'),
('Misión en París (Alatriste 8)', 5, 6, 1, 'Disponible'),
('Rose in Chains', 6, 2, 1, 'Disponible'),
('Catábasis', 7, 3, 1, 'Disponible'),
('La asistenta', 8, 2, 1, 'Disponible'),
('Cien años de soledad', 9, 1, 1, 'Disponible'),
('La ciudad y los perros', 10, 1, 1, 'Disponible');

-- Asociar autores con libros
INSERT INTO libro_autor (id_autor, id_libro) VALUES 
(1, 1), -- Rebeca Yarros - Alas de sangre
(2, 2), -- Iryna Zubkova - Acelerando en rojo
(3, 3), -- Mercedes Ron - Marfil
(4, 4), -- Joana Marcús - Antes de Diciembre
(5, 5), -- Arturo Pérez-Reverte - Misión en París
(6, 6), -- Freida McFadden - Rose in Chains
(7, 7), -- R. F. Kuang - Catábasis
(6, 8), -- Freida McFadden - La asistenta
(8, 9), -- Gabriel García Márquez - Cien años de soledad
(9, 10); -- Mario Vargas Llosa - La ciudad y los perros

-- Insertar ejemplares
INSERT INTO ejemplar (cantidad_ejemplar, localizacion, id_libro) VALUES 
(5, 'Estante A-1, Sede Lima', 1),
(3, 'Estante A-2, Sede Chiclayo', 1),
(4, 'Estante B-1, Sede Lima', 2),
(2, 'Estante B-2, Sede Piura', 2),
(6, 'Estante C-1, Sede Chiclayo', 3),
(3, 'Estante C-2, Sede Lima', 3),
(7, 'Estante A-3, Sede Lima', 4),
(2, 'Estante A-4, Sede Chiclayo', 4),
(4, 'Estante D-1, Sede Lima', 5),
(3, 'Estante D-2, Sede Piura', 5),
(5, 'Estante E-1, Sede Lima', 6),
(2, 'Estante E-2, Sede Chiclayo', 6),
(3, 'Estante F-1, Sede Lima', 7),
(1, 'Estante F-2, Sede Piura', 7),
(4, 'Estante G-1, Sede Lima', 8),
(2, 'Estante G-2, Sede Chiclayo', 8),
(8, 'Estante H-1, Sede Lima', 9),
(5, 'Estante H-2, Sede Chiclayo', 9),
(6, 'Estante I-1, Sede Lima', 10),
(3, 'Estante I-2, Sede Piura', 10);

-- Insertar algunos préstamos de ejemplo
INSERT INTO prestamos (fecha_prestamo, fecha_devolucion, id_usuarios, id_ejemplar) VALUES 
('2024-01-15', '2024-01-29', 4, 1),
('2024-01-20', '2024-02-03', 5, 3),
('2024-01-25', '2024-02-08', 4, 5);

-- Insertar algunos pagos de ejemplo
INSERT INTO pago (multa, fecha_pago, estado, id_prestamo) VALUES 
(0.00, '2024-01-15 10:30:00', 1, 1),
(5.50, '2024-01-20 14:15:00', 1, 2),
(0.00, '2024-01-25 09:45:00', 1, 3);

-- Insertar historial de préstamos
INSERT INTO historial_prestamos (id_prestamo, id_ejemplar, id_usuario, fecha_prestamo, fecha_real_devolucion) VALUES 
(1, 1, 4, '2024-01-15', '2024-01-29'),
(2, 3, 5, '2024-01-20', '2024-02-03'),
(3, 5, 4, '2024-01-25', '2024-02-08');

-- Insertar historial de bibliotecarios
INSERT INTO historial_bibliotecario (id_bibliotecario, fecha_acceso, fecha_salida, hora_entrada, hora_salida) VALUES 
(1, '2024-01-15', '2024-01-15', '08:00:00', '17:00:00'),
(2, '2024-01-15', '2024-01-15', '08:30:00', '16:30:00'),
(1, '2024-01-16', '2024-01-16', '08:00:00', '17:00:00');

-- Insertar roles y permisos (si se necesitan)
INSERT INTO rol (nombreRol) VALUES 
('ROLE_ADMIN'),
('ROLE_BIBLIOTECARIO'),
('ROLE_USER');

INSERT INTO permiso (nombrePermiso) VALUES 
('READ'),
('WRITE'),
('DELETE'),
('UPDATE'),
('MANAGE_USERS'),
('MANAGE_BOOKS'),
('MANAGE_LOANS');

-- =====================================================
-- CREAR ÍNDICES PARA OPTIMIZAR CONSULTAS
-- =====================================================

-- Índices en tabla usuarios
CREATE INDEX idx_usuarios_usuario ON usuarios(usuario);
CREATE INDEX idx_usuarios_correo ON usuarios(correo);
CREATE INDEX idx_usuarios_estado ON usuarios(estado);

-- Índices en tabla libros
CREATE INDEX idx_libro_titulo ON libro(titulo);
CREATE INDEX idx_libro_estado ON libro(estado);

-- Índices en tabla préstamos
CREATE INDEX idx_prestamos_fecha_prestamo ON prestamos(fecha_prestamo);
CREATE INDEX idx_prestamos_fecha_devolucion ON prestamos(fecha_devolucion);
CREATE INDEX idx_prestamos_usuario ON prestamos(id_usuarios);

-- Índices en tabla ejemplares
CREATE INDEX idx_ejemplar_libro ON ejemplar(id_libro);
CREATE INDEX idx_ejemplar_localizacion ON ejemplar(localizacion);

-- =====================================================
-- CONFIGURACIONES ADICIONALES
-- =====================================================

-- Configurar el motor de almacenamiento
ALTER TABLE usuarios ENGINE=InnoDB;
ALTER TABLE libro ENGINE=InnoDB;
ALTER TABLE prestamos ENGINE=InnoDB;
ALTER TABLE ejemplar ENGINE=InnoDB;

-- =====================================================
-- VERIFICACIÓN FINAL
-- =====================================================

-- Mostrar resumen de tablas creadas
SELECT 
    TABLE_NAME as 'Tabla',
    TABLE_ROWS as 'Filas',
    CREATE_TIME as 'Creada'
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = 'biblioteca_bmc'
ORDER BY TABLE_NAME;

-- Mostrar usuarios creados
SELECT 
    u.id,
    u.nombre,
    u.usuario,
    u.correo,
    p.nombre as perfil
FROM usuarios u
LEFT JOIN perfiles p ON u.id_perfil = p.id;

-- Mostrar libros con sus autores
SELECT 
    l.id_libro,
    l.titulo,
    a.nombre_autor,
    e.nombre_editorial,
    g.nombre_genero,
    c.nombre_categoria
FROM libro l
LEFT JOIN libro_autor la ON l.id_libro = la.id_libro
LEFT JOIN autor a ON la.id_autor = a.id_autor
LEFT JOIN editorial e ON l.id_editorial = e.id_editorial
LEFT JOIN genero g ON l.id_genero = g.id_genero
LEFT JOIN categoria c ON l.id_categoria = c.id_categoria
ORDER BY l.id_libro;

-- =====================================================
-- SCRIPT COMPLETADO
-- =====================================================

-- Para usar este script:
-- 1. Abre phpMyAdmin
-- 2. Ve a la pestaña "SQL"
-- 3. Copia y pega todo este contenido
-- 4. Ejecuta el script
-- 5. Verifica que todas las tablas se crearon correctamente

-- Credenciales de prueba:
-- Usuario: admin, Contraseña: 123456 (Administrador)
-- Usuario: mgarcia, Contraseña: 123456 (Bibliotecario)
-- Usuario: ausuario, Contraseña: 123456 (Usuario regular)

SELECT 'Script ejecutado exitosamente. Base de datos biblioteca_bmc creada.' as Resultado;
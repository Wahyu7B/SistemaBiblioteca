package com.example.acceso.service;

import com.example.acceso.model.Usuario;
import com.example.acceso.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAllByEstadoNot(2);
    }

    @Transactional
    public Usuario guardarUsuario(Usuario usuario) {
        try {
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre es obligatorio");
            }

            if (usuario.getPerfil() == null) {
                throw new IllegalArgumentException("El perfil es obligatorio");
            }


            if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
                throw new IllegalArgumentException("El usuario es obligatorio");
            }

            if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
                throw new IllegalArgumentException("El correo es obligatorio");
            }

            // Normalizar datos
            usuario.setNombre(usuario.getNombre().trim());
            usuario.setUsuario(usuario.getUsuario().trim().toLowerCase());
            usuario.setCorreo(usuario.getCorreo().trim().toLowerCase());

            if (usuario.getId() != null) {
                Optional<Usuario> usuarioExistente = obtenerUsuarioPorId(usuario.getId());
                if (usuarioExistente.isPresent()) {

                    if (usuario.getClave() == null || usuario.getClave().trim().isEmpty()) {
                        usuario.setClave(usuarioExistente.get().getClave());
                    } else {

                        usuario.setClave(passwordEncoder.encode(usuario.getClave().trim()));
                    }
                } else {
                    throw new IllegalArgumentException("Usuario no encontrado para actualizar");
                }
            } else {

                if (usuario.getClave() == null || usuario.getClave().trim().isEmpty()) {
                    throw new IllegalArgumentException("La contraseña es obligatoria para nuevos usuarios");
                }
                usuario.setClave(passwordEncoder.encode(usuario.getClave().trim()));
                usuario.setEstado(1);
            }

            return usuarioRepository.save(usuario);

        } catch (DataIntegrityViolationException e) {
            String message = e.getMessage().toLowerCase();
            if (message.contains("usuario")) {
                throw new IllegalArgumentException("El nombre de usuario ya existe");
            } else if (message.contains("correo") || message.contains("email")) {
                throw new IllegalArgumentException("El correo electrónico ya está registrado");
            } else {
                throw new IllegalArgumentException("Error de integridad de datos");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el usuario: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public long contarUsuarios() {
        return usuarioRepository.countByEstadoNot(2);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario.trim().toLowerCase());
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de usuario invÃ¡lido");
        }

        Usuario usuario = obtenerUsuarioPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setEstado(2);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public Optional<Usuario> cambiarEstadoUsuario(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }

        return obtenerUsuarioPorId(id).map(usuario -> {
            if (usuario.getEstado() == 1) {
                usuario.setEstado(0);
            } else if (usuario.getEstado() == 0) {
                usuario.setEstado(1); 
            }
            return usuarioRepository.save(usuario);
        });
    }


    @Transactional(readOnly = true)
    public boolean existeUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            return false;
        }
        return usuarioRepository.existsByUsuario(nombreUsuario.trim().toLowerCase());
    }

    @Transactional(readOnly = true)
    public boolean existeCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        return usuarioRepository.existsByCorreo(correo.trim().toLowerCase());
    }


    public boolean verificarContrasena(String contrasenaTextoPlano, String contrasenaEncriptada) {
        return passwordEncoder.matches(contrasenaTextoPlano, contrasenaEncriptada);
    }
}
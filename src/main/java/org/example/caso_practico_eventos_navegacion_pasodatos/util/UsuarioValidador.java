package org.example.caso_practico_eventos_navegacion_pasodatos.util;

import org.example.caso_practico_eventos_navegacion_pasodatos.models.Usuario;

public class UsuarioValidador implements Validador<Usuario> {

    @Override
    public ResultadoValidacion validar(Usuario usuario) {
        if (usuario == null) {
            return ResultadoValidacion.error("Los datos de usuario no pueden ser nulos.");
        }

        if (usuario.getUsername() == null || usuario.getUsername().isBlank()) {
            return ResultadoValidacion.error("El campo de usuario es obligatorio.");
        }

        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            return ResultadoValidacion.error("El campo de contraseña es obligatorio.");
        }

        return ResultadoValidacion.exito();
    }
}
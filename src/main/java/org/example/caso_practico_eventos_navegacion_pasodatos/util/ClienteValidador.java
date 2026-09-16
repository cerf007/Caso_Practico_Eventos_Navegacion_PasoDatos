package org.example.caso_practico_eventos_navegacion_pasodatos.util;


import org.example.caso_practico_eventos_navegacion_pasodatos.models.Cliente;

public class ClienteValidador implements Validador<Cliente> {

    @Override
    public ResultadoValidacion validar(Cliente cliente) {
        if (cliente == null) {
            return ResultadoValidacion.error("El registro del cliente no puede ser nulo.");
        }

        if (cliente.getNombres() == null || cliente.getNombres().isBlank()) {
            return ResultadoValidacion.error("El nombre del cliente es obligatorio.");
        }

        if (cliente.getApellidos() == null || cliente.getApellidos().isBlank()) {
            return ResultadoValidacion.error("El apellido del cliente es obligatorio.");
        }

        if (cliente.getTipoCliente() == null || cliente.getTipoCliente().isBlank()) {
            return ResultadoValidacion.advertencia("Debe seleccionar un tipo de cliente.");
        }

        if (cliente.getCiudad() == null || cliente.getCiudad().isBlank()) {
            return ResultadoValidacion.advertencia("Debe seleccionar una ciudad.");
        }

        if (cliente.getFechaNacimiento() == null) {
            return ResultadoValidacion.error("Debe ingresar la fecha de nacimiento.");
        }

        if (cliente.getTipoSolicitud() == null || cliente.getTipoSolicitud().isBlank()) {
            return ResultadoValidacion.error("Debe seleccionar el tipo de solicitud.");
        }

        if (cliente.getServiciosInteres() == null || cliente.getServiciosInteres().isEmpty()) {
            return ResultadoValidacion.advertencia("Debe seleccionar al menos un servicio de interés.");
        }

        return ResultadoValidacion.exito();
    }
}
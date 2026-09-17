package org.example.caso_practico_eventos_navegacion_pasodatos.util;

import org.example.caso_practico_eventos_navegacion_pasodatos.models.Cliente;

import java.time.LocalDate;
import java.time.Period;

public class ClienteValidador implements Validador<Cliente> {

    @Override
    public ResultadoValidacion validar(Cliente cliente) {
        LocalDate hoy = LocalDate.now();

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

        if (cliente.getFechaNacimiento().isAfter(hoy) || cliente.getFechaNacimiento().isEqual(hoy)) {
            return ResultadoValidacion.error("La fecha de nacimiento debe ser anterior al día de hoy.");
        }

        Period edad = Period.between(cliente.getFechaNacimiento(), hoy);
        if (edad.getYears() < 18) {
            return ResultadoValidacion.advertencia("El cliente registrado es menor de edad (" + edad.getYears() + " años).");
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
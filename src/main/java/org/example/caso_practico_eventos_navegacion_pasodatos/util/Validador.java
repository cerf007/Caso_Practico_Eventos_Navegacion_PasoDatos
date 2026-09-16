package org.example.caso_practico_eventos_navegacion_pasodatos.util;

public interface Validador<T> {
    ResultadoValidacion validar(T objeto);
}

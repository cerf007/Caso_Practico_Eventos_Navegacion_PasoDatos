package org.example.caso_practico_eventos_navegacion_pasodatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    private String idSolicitud;
    private String tipoSolicitud;
    private List<String> serviciosInteres;
    private LocalDateTime fechaCreacion;
}
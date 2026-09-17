package org.example.caso_practico_eventos_navegacion_pasodatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private String id;
    private String nombres;
    private String apellidos;
    private String tipoCliente;
    private String ciudad;
    private LocalDate fechaNacimiento;
    private String tipoSolicitud;
    private List<String> serviciosInteres;
    private String rutaFotografia;


    public String getNombreCompleto() {
        return ((nombres != null ? nombres : "") + " " + (apellidos != null ? apellidos : "")).trim();
    }
}
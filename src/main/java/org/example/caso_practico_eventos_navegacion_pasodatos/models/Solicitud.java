package org.example.caso_practico_eventos_navegacion_pasodatos.models;

import java.time.LocalDateTime;
import java.util.List;

public class Solicitud {
    private String idSolicitud;
    private String tipoSolicitud;
    private List<String> serviciosInteres;
    private LocalDateTime fechaCreacion;

    public Solicitud() {
    }

    public Solicitud(String idSolicitud, String tipoSolicitud, List<String> serviciosInteres, LocalDateTime fechaCreacion) {
        this.idSolicitud = idSolicitud;
        this.tipoSolicitud = tipoSolicitud;
        this.serviciosInteres = serviciosInteres;
        this.fechaCreacion = fechaCreacion;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public List<String> getServiciosInteres() {
        return serviciosInteres;
    }

    public void setServiciosInteres(List<String> serviciosInteres) {
        this.serviciosInteres = serviciosInteres;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}

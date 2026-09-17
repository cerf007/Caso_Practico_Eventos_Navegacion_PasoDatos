package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.caso_practico_eventos_navegacion_pasodatos.models.Cliente;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.SceneManager;

public class DetalleClienteController {

    @FXML private Label lblNombreCompleto;
    @FXML private Label lblTipoCliente;
    @FXML private Label lblCiudad;
    @FXML private Label lblFechaNacimiento;
    @FXML private Label lblTipoSolicitud;
    @FXML private Label lblServicios;
    @FXML private ImageView imgFoto;
    @FXML private Button btnCerrar;

    public void setCliente(Cliente cliente) {
        if (cliente == null) return;

        lblNombreCompleto.setText(cliente.getNombreCompleto());
        lblTipoCliente.setText(valor(cliente.getTipoCliente()));
        lblCiudad.setText(valor(cliente.getCiudad()));
        lblFechaNacimiento.setText(cliente.getFechaNacimiento() != null ? cliente.getFechaNacimiento().toString() : "No ingresada");
        lblTipoSolicitud.setText(valor(cliente.getTipoSolicitud()));

        String servicios = cliente.getServiciosInteres() != null && !cliente.getServiciosInteres().isEmpty()
                ? String.join(", ", cliente.getServiciosInteres())
                : "Ninguno";
        lblServicios.setText(servicios);

        if (cliente.getRutaFotografia() != null && !cliente.getRutaFotografia().isBlank()) {
            try {
                imgFoto.setImage(new Image(cliente.getRutaFotografia()));
            } catch (IllegalArgumentException exception) {
                imgFoto.setImage(null);
            }
        }
    }

    @FXML
    void cerrarVentana() {
        SceneManager.cerrarVentana(btnCerrar);
    }

    private String valor(String texto) {
        return texto == null || texto.isBlank() ? "No ingresado" : texto;
    }
}
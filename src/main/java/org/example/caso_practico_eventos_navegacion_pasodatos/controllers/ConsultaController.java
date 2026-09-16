package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.caso_practico_eventos_navegacion_pasodatos.models.Cliente;

public class ConsultaController {

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colNombres;
    @FXML private TableColumn<Cliente, String> colTipo;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, String> colSolicitud;
    @FXML private Button btnCerrar;

    public void setListaClientes(ObservableList<Cliente> listaClientes) {
        tblClientes.setItems(listaClientes);
    }

    @FXML
    public void initialize() {
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));
    }

    @FXML
    void manejarClicTabla(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Cliente seleccionado = tblClientes.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                mostrarDetalleCliente(seleccionado);
            }
        }
    }

    private void mostrarDetalleCliente(Cliente cliente) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Detalle del Cliente");
        dialog.setHeaderText("Información completa de: " + cliente.getNombreCompleto());

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);

        VBox vbox = new VBox(10);
        vbox.getChildren().add(new Label("ID: " + cliente.getId()));
        vbox.getChildren().add(new Label("Tipo de Cliente: " + cliente.getTipoCliente()));
        vbox.getChildren().add(new Label("Ciudad: " + cliente.getCiudad()));
        vbox.getChildren().add(new Label("Nacimiento: " + cliente.getFechaNacimiento()));
        vbox.getChildren().add(new Label("Solicitud: " + cliente.getTipoSolicitud()));

        String servicios = cliente.getServiciosInteres() != null
                ? String.join(", ", cliente.getServiciosInteres())
                : "Ninguno";
        vbox.getChildren().add(new Label("Servicios de Interés: " + servicios));

        if (cliente.getRutaFotografia() != null && !cliente.getRutaFotografia().isEmpty()) {
            try {
                ImageView img = new ImageView(new Image(cliente.getRutaFotografia()));
                img.setFitHeight(100);
                img.setFitWidth(100);
                img.setPreserveRatio(true);
                vbox.getChildren().add(img);
            } catch (IllegalArgumentException exception) {
                System.out.println("No se pudo cargar la imagen: " + cliente.getRutaFotografia());
            }
        }

        dialogPane.setContent(vbox);
        dialog.showAndWait();
    }

    @FXML
    void cerrarVentana() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}

package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.example.caso_practico_eventos_navegacion_pasodatos.models.Cliente;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.SceneManager;

import java.io.File;
import java.io.IOException;

public class MenuPrincipalController {

    @FXML private BorderPane rootPane;
    @FXML private ContextMenu contextMenu;

    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        MenuItem itemConsultar = new MenuItem("Consultar clientes");
        itemConsultar.setOnAction(this::abrirConsulta);

        MenuItem itemResumen = new MenuItem("Ver total de clientes registrados");
        itemResumen.setOnAction(e -> mostrarTotalClientes());

        contextMenu.getItems().addAll(itemConsultar, itemResumen);
        rootPane.setOnContextMenuRequested(e -> contextMenu.show(rootPane, e.getScreenX(), e.getScreenY()));
    }

    @FXML
    void abrirRegistro(ActionEvent event) {
        try {
            SceneManager.<ClienteController>abrirVentanaModal(
                    "/org/example/caso_practico_eventos_navegacion_pasodatos/Cliente-view.fxml",
                    "Registro de Cliente",
                    controller -> controller.setListaClientes(listaClientes)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirConsulta(ActionEvent event) {
        try {
            SceneManager.<ConsultaController>abrirVentanaModal(
                    "/org/example/caso_practico_eventos_navegacion_pasodatos/Consulta-view.fxml",
                    "Consulta de Clientes",
                    controller -> controller.setListaClientes(listaClientes)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exportarDatos(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta para exportar reportes");
        File selectedDirectory = directoryChooser.showDialog(rootPane.getScene().getWindow());

        if (selectedDirectory != null) {
            System.out.println("Carpeta seleccionada: " + selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    void refrescarSistema(ActionEvent event) {
        mostrarTotalClientes();
    }

    @FXML
    void mostrarDialogoSistema(ActionEvent event) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Información del sistema");
        dialog.setHeaderText("Sistema de registro de clientes");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);

        VBox contenido = new VBox(8);
        contenido.getChildren().add(new Label("Clientes registrados: " + listaClientes.size()));
        contenido.getChildren().add(new Label("Use el menú o la barra de herramientas para navegar."));
        contenido.getChildren().add(new Label("El registro, consulta y detalle comparten datos en memoria."));

        dialogPane.setContent(contenido);
        dialog.showAndWait();
    }

    private void mostrarTotalClientes() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resumen de clientes");
        alert.setHeaderText("Clientes registrados");
        alert.setContentText("Total de clientes en memoria: " + listaClientes.size());
        alert.showAndWait();
    }
}
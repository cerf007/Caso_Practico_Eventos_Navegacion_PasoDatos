package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.caso_practico_eventos_navegacion_pasodatos.models.Cliente;

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

        rootPane.setOnContextMenuRequested(e ->
                contextMenu.show(rootPane, e.getScreenX(), e.getScreenY()));
    }

    @FXML
    void abrirRegistro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/caso_practico_eventos_navegacion_pasodatos/Cliente-view.fxml"));
            Parent root = loader.load();

            ClienteController controller = loader.getController();
            controller.setListaClientes(listaClientes);

            Stage stage = new Stage();
            stage.setTitle("Registro de Cliente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirConsulta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/caso_practico_eventos_navegacion_pasodatos/Consulta-view.fxml"));
            Parent root = loader.load();

            ConsultaController controller = loader.getController();
            controller.setListaClientes(listaClientes);

            Stage stage = new Stage();
            stage.setTitle("Consulta de Clientes");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exportarDatos(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta para exportar reportes");
        Stage stage = (Stage) rootPane.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

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
        dialog.setTitle("Informacion del sistema");
        dialog.setHeaderText("Sistema de registro de clientes");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);

        VBox contenido = new VBox(8);
        contenido.getChildren().add(new Label("Clientes registrados: " + listaClientes.size()));
        contenido.getChildren().add(new Label("Use el menu o la barra de herramientas para navegar."));
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

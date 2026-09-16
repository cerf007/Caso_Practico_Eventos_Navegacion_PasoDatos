package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.caso_practico_eventos_navegacion_pasodatos.models.Cliente;

import java.io.File;
import java.io.IOException;

public class MenuPrincipalController {

    @FXML private BorderPane rootPane;
    @FXML private ContextMenu contextMenu;

    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        MenuItem itemActualizar = new MenuItem("Refrescar sistema");
        itemActualizar.setOnAction(e -> System.out.println("Sistema refrescado"));
        contextMenu.getItems().add(itemActualizar);

        rootPane.setOnContextMenuRequested(e ->
                contextMenu.show(rootPane, e.getScreenX(), e.getScreenY()));
    }

    @FXML
    void abrirRegistro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/caso_practico_eventos_navegacion_pasodatos/RegistroCliente.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/caso_practico_eventos_navegacion_pasodatos/ConsultaCliente.fxml"));
            Parent root = loader.load();

            ClienteController controller = loader.getController();
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
}

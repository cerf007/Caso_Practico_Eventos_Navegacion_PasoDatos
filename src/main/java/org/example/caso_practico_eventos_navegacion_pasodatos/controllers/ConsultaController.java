package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.caso_practico_eventos_navegacion_pasodatos.models.Cliente;

import java.io.IOException;
import java.time.LocalDate;

public class ConsultaController {

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colNombres;
    @FXML private TableColumn<Cliente, String> colTipo;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, LocalDate> colFechaNacimiento;
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
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/caso_practico_eventos_navegacion_pasodatos/DetalleCliente-view.fxml"));
            Parent root = loader.load();

            DetalleClienteController controller = loader.getController();
            controller.setCliente(cliente);

            Stage stage = new Stage();
            stage.setTitle("Detalle del Cliente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cerrarVentana() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}

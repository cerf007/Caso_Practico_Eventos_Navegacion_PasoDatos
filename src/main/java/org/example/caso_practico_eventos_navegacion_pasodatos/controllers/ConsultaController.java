package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.caso_practico_eventos_navegacion_pasodatos.models.Cliente;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class ConsultaController {

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colId;
    @FXML private TableColumn<Cliente, String> colNombres;
    @FXML private TableColumn<Cliente, String> colTipo;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, LocalDate> colFechaNacimiento;
    @FXML private TableColumn<Cliente, String> colSolicitud;
    @FXML private TableColumn<Cliente, Void> colAcciones;
    @FXML private Label lblRegistrosEncontrados;
    @FXML private Button btnCerrar;

    public void setListaClientes(ObservableList<Cliente> listaClientes) {
        tblClientes.setItems(listaClientes);
        actualizarConteoRegistros();
        listaClientes.addListener((javafx.collections.ListChangeListener<Cliente>) change -> actualizarConteoRegistros());
    }

    @FXML
    public void initialize() {
        configurarColumnaId();
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));
        configurarColumnaAcciones();
        actualizarConteoRegistros();
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

    private void configurarColumnaAcciones() {
        colAcciones.setCellFactory(columna -> new TableCell<>() {
            private final Button btnVer = crearBotonAccion("\uD83D\uDC41", "Ver cliente");
            private final Button btnEditar = crearBotonAccion("\u270E", "Editar cliente");
            private final Button btnEliminar = crearBotonAccion("\uD83D\uDDD1", "Eliminar cliente");
            private final HBox contenedor = new HBox(6, btnVer, btnEditar, btnEliminar);

            {
                btnVer.setOnAction(event -> mostrarDetalleCliente(getTableView().getItems().get(getIndex())));
                btnEditar.setOnAction(event -> editarCliente(getTableView().getItems().get(getIndex())));
                btnEliminar.setOnAction(event -> eliminarCliente(getTableView().getItems().get(getIndex())));
                contenedor.setStyle("-fx-alignment: center;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : contenedor);
            }
        });
    }

    private void configurarColumnaId() {
        colId.setCellFactory(columna -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(getIndex() + 1));
            }
        });
    }

    private Button crearBotonAccion(String texto, String ayuda) {
        Button boton = new Button(texto);
        boton.setTooltip(new Tooltip(ayuda));
        boton.setMinWidth(32);
        boton.setMinHeight(28);
        boton.setStyle("-fx-background-color: #eef8fa; -fx-border-color: #9fc8d0; -fx-border-radius: 7; -fx-background-radius: 7; -fx-text-fill: #0e5160; -fx-font-size: 16px; -fx-font-family: 'Segoe UI Emoji'; -fx-padding: 3 8;");
        return boton;
    }

    private void editarCliente(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/caso_practico_eventos_navegacion_pasodatos/Cliente-view.fxml"));
            Parent root = loader.load();

            ClienteController controller = loader.getController();
            controller.setListaClientes(tblClientes.getItems());
            controller.setClienteEditar(cliente);

            Stage stage = new Stage();
            stage.setTitle("Editar Cliente");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            tblClientes.refresh();
            actualizarConteoRegistros();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eliminarCliente(Cliente cliente) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar cliente");
        alert.setHeaderText("Eliminar registro seleccionado");
        alert.setContentText("Desea eliminar a " + cliente.getNombreCompleto() + "?");

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            tblClientes.getItems().remove(cliente);
            actualizarConteoRegistros();
        }
    }

    private void actualizarConteoRegistros() {
        if (lblRegistrosEncontrados == null || tblClientes == null || tblClientes.getItems() == null) {
            return;
        }

        int total = tblClientes.getItems().size();
        lblRegistrosEncontrados.setText(total + (total == 1 ? " registro encontrado" : " registros encontrados"));
    }

    @FXML
    void cerrarVentana() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}

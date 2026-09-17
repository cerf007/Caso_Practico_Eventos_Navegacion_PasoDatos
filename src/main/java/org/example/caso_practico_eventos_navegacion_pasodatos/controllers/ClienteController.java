package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.caso_practico_eventos_navegacion_pasodatos.models.Cliente;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.ClienteValidador;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.ResultadoValidacion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClienteController {

    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private ComboBox<String> cmbTipoCliente;
    @FXML private ComboBox<String> cmbCiudad;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private ToggleGroup tgTipoSolicitud;
    @FXML private CheckBox chkSoporteTecnico;
    @FXML private CheckBox chkMantenimiento;
    @FXML private ListView<String> lstServiciosSeleccionados;
    @FXML private ImageView imgFoto;
    @FXML private Button btnCancelar;

    private String rutaImagenSeleccionada = "";
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    public void setListaClientes(ObservableList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @FXML
    public void initialize() {
        cmbTipoCliente.getItems().addAll("Persona Natural", "Empresa");
        cmbCiudad.getItems().addAll(
                "Boaco",
                "Bluefields",
                "Chinandega",
                "Esteli",
                "Granada",
                "Jinotega",
                "Jinotepe",
                "Juigalpa",
                "Leon",
                "Managua",
                "Masaya",
                "Matagalpa",
                "Ocotal",
                "Puerto Cabezas",
                "Rivas",
                "San Carlos",
                "Somoto"
        );

        txtNombres.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[a-zA-Z\\s]")) {
                event.consume();
            }
        });

        chkSoporteTecnico.setOnAction(this::actualizarServiciosSeleccionados);
        chkMantenimiento.setOnAction(this::actualizarServiciosSeleccionados);
    }

    @FXML
    void seleccionarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Fotografia");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) txtNombres.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            rutaImagenSeleccionada = file.toURI().toString();
            imgFoto.setImage(new Image(rutaImagenSeleccionada));
        }
    }

    @FXML
    void guardar(ActionEvent event) {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setId(UUID.randomUUID().toString());
        nuevoCliente.setNombres(txtNombres.getText());
        nuevoCliente.setApellidos(txtApellidos.getText());
        nuevoCliente.setTipoCliente(cmbTipoCliente.getValue());
        nuevoCliente.setCiudad(cmbCiudad.getValue());
        nuevoCliente.setFechaNacimiento(dpFechaNacimiento.getValue());
        nuevoCliente.setRutaFotografia(rutaImagenSeleccionada);

        RadioButton rbSeleccionado = (RadioButton) tgTipoSolicitud.getSelectedToggle();
        if (rbSeleccionado != null) {
            nuevoCliente.setTipoSolicitud(rbSeleccionado.getText());
        }

        List<String> servicios = new ArrayList<>();
        if (chkSoporteTecnico.isSelected()) {
            servicios.add("Soporte tecnico");
        }
        if (chkMantenimiento.isSelected()) {
            servicios.add("Mantenimiento");
        }
        nuevoCliente.setServiciosInteres(servicios);

        ClienteValidador validador = new ClienteValidador();
        ResultadoValidacion resultado = validador.validar(nuevoCliente);

        if (!resultado.isValido()) {
            Alert.AlertType tipoAlerta = resultado.getTipo() == ResultadoValidacion.Tipo.ADVERTENCIA
                    ? Alert.AlertType.WARNING
                    : Alert.AlertType.ERROR;
            Alert alert = new Alert(tipoAlerta, resultado.getMensaje());
            alert.setHeaderText("Error de Validacion");
            alert.showAndWait();
            return;
        }

        listaClientes.add(nuevoCliente);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cliente registrado exitosamente.");
        alert.showAndWait();
        limpiar(null);
    }

    @FXML
    void limpiar(ActionEvent event) {
        txtNombres.clear();
        txtApellidos.clear();
        cmbTipoCliente.getSelectionModel().clearSelection();
        cmbCiudad.getSelectionModel().clearSelection();
        dpFechaNacimiento.setValue(null);
        if (tgTipoSolicitud.getSelectedToggle() != null) {
            tgTipoSolicitud.getSelectedToggle().setSelected(false);
        }
        chkSoporteTecnico.setSelected(false);
        chkMantenimiento.setSelected(false);
        actualizarServiciosSeleccionados(null);
        imgFoto.setImage(null);
        rutaImagenSeleccionada = "";
    }

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void actualizarServiciosSeleccionados(ActionEvent event) {
        lstServiciosSeleccionados.getItems().clear();
        if (chkSoporteTecnico.isSelected()) {
            lstServiciosSeleccionados.getItems().add("Soporte tecnico");
        }
        if (chkMantenimiento.isSelected()) {
            lstServiciosSeleccionados.getItems().add("Mantenimiento");
        }
    }
}

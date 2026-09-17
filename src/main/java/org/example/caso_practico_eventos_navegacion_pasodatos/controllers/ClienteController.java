package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import org.example.caso_practico_eventos_navegacion_pasodatos.models.Cliente;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.ClienteValidador;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.ResultadoValidacion;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.SceneManager;

import java.io.File;
import java.time.LocalDate;
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
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    private String rutaImagenSeleccionada = "";
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private Cliente clienteEditando;

    public void setListaClientes(ObservableList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public void setClienteEditar(Cliente cliente) {
        this.clienteEditando = cliente;
        cargarClienteEnFormulario(cliente);
        btnGuardar.setText("Actualizar");
    }

    @FXML
    public void initialize() {
        cmbTipoCliente.getItems().addAll("Persona Natural", "Empresa");
        cmbCiudad.getItems().addAll(
                "Boaco", "Bluefields", "Chinandega", "Estelí", "Granada", "Jinotega",
                "Jinotepe", "Juigalpa", "León", "Managua", "Masaya", "Matagalpa",
                "Ocotal", "Puerto Cabezas", "Rivas", "San Carlos", "Somoto"
        );

        txtNombres.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]")) event.consume();
        });
        txtApellidos.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]")) event.consume();
        });

        dpFechaNacimiento.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date != null && !empty && !date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #f0f0f0;");
                }
            }
        });

        chkSoporteTecnico.setOnAction(this::actualizarServiciosSeleccionados);
        chkMantenimiento.setOnAction(this::actualizarServiciosSeleccionados);
    }

    @FXML
    void seleccionarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Fotografía");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(txtNombres.getScene().getWindow());

        if (file != null) {
            rutaImagenSeleccionada = file.toURI().toString();
            imgFoto.setImage(new Image(rutaImagenSeleccionada));
        }
    }

    @FXML
    void guardar(ActionEvent event) {
        Cliente cliente = clienteEditando != null ? clienteEditando : new Cliente();
        if (cliente.getId() == null || cliente.getId().isBlank()) cliente.setId(UUID.randomUUID().toString());

        cliente.setNombres(txtNombres.getText());
        cliente.setApellidos(txtApellidos.getText());
        cliente.setTipoCliente(cmbTipoCliente.getValue());
        cliente.setCiudad(cmbCiudad.getValue());
        cliente.setFechaNacimiento(dpFechaNacimiento.getValue());
        cliente.setRutaFotografia(rutaImagenSeleccionada);

        RadioButton rbSeleccionado = (RadioButton) tgTipoSolicitud.getSelectedToggle();
        if (rbSeleccionado != null) cliente.setTipoSolicitud(rbSeleccionado.getText());

        List<String> servicios = new ArrayList<>();
        if (chkSoporteTecnico.isSelected()) servicios.add("Soporte técnico");
        if (chkMantenimiento.isSelected()) servicios.add("Mantenimiento");
        cliente.setServiciosInteres(servicios);

        ClienteValidador validador = new ClienteValidador();
        ResultadoValidacion resultado = validador.validar(cliente);

        if (resultado.getTipo() == ResultadoValidacion.Tipo.ERROR) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Validación", resultado.getMensaje());
            return;
        } else if (resultado.getTipo() == ResultadoValidacion.Tipo.ADVERTENCIA) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", resultado.getMensaje());
            return;
        }

        if (clienteEditando == null) {
            listaClientes.add(cliente);
        } else {
            int indice = listaClientes.indexOf(clienteEditando);
            if (indice >= 0) listaClientes.set(indice, clienteEditando);
        }

        String mensaje = clienteEditando == null ? "Cliente registrado exitosamente." : "Cliente actualizado exitosamente.";
        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", mensaje);

        if (clienteEditando == null) limpiar(null);
        else cancelar(null);
    }

    @FXML
    void limpiar(ActionEvent event) {
        txtNombres.clear();
        txtApellidos.clear();
        cmbTipoCliente.getSelectionModel().clearSelection();
        cmbCiudad.getSelectionModel().clearSelection();
        dpFechaNacimiento.setValue(null);
        if (tgTipoSolicitud.getSelectedToggle() != null) tgTipoSolicitud.getSelectedToggle().setSelected(false);
        chkSoporteTecnico.setSelected(false);
        chkMantenimiento.setSelected(false);
        actualizarServiciosSeleccionados(null);
        imgFoto.setImage(null);
        rutaImagenSeleccionada = "";

        clienteEditando = null;
        btnGuardar.setText("Guardar");
    }

    @FXML
    void cancelar(ActionEvent event) {
        SceneManager.cerrarVentana(btnCancelar);
    }

    @FXML
    void actualizarServiciosSeleccionados(ActionEvent event) {
        lstServiciosSeleccionados.getItems().clear();
        if (chkSoporteTecnico.isSelected()) lstServiciosSeleccionados.getItems().add("Soporte técnico");
        if (chkMantenimiento.isSelected()) lstServiciosSeleccionados.getItems().add("Mantenimiento");
    }

    private void cargarClienteEnFormulario(Cliente cliente) {
        if (cliente == null) return;
        txtNombres.setText(cliente.getNombres());
        txtApellidos.setText(cliente.getApellidos());
        cmbTipoCliente.setValue(cliente.getTipoCliente());
        cmbCiudad.setValue(cliente.getCiudad());
        dpFechaNacimiento.setValue(cliente.getFechaNacimiento());
        rutaImagenSeleccionada = cliente.getRutaFotografia() != null ? cliente.getRutaFotografia() : "";

        if (!rutaImagenSeleccionada.isBlank()) {
            try { imgFoto.setImage(new Image(rutaImagenSeleccionada)); }
            catch (Exception ignored) { imgFoto.setImage(null); }
        }

        if (cliente.getTipoSolicitud() != null) {
            for (Toggle toggle : tgTipoSolicitud.getToggles()) {
                if (cliente.getTipoSolicitud().equals(((RadioButton) toggle).getText())) {
                    tgTipoSolicitud.selectToggle(toggle);
                    break;
                }
            }
        }

        List<String> servicios = cliente.getServiciosInteres();
        chkSoporteTecnico.setSelected(servicios != null && servicios.contains("Soporte técnico"));
        chkMantenimiento.setSelected(servicios != null && servicios.contains("Mantenimiento"));
        actualizarServiciosSeleccionados(null);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo, mensaje);
        alert.setHeaderText(titulo);
        alert.showAndWait();
    }
}
package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
// Ya no necesitas los imports de KeyCode ni KeyEvent
import org.example.caso_practico_eventos_navegacion_pasodatos.models.Usuario;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.ResultadoValidacion;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.SceneManager;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.UsuarioValidador;

import java.io.IOException;
import java.util.Optional;

public class IniciarSesionController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnIniciar;


    @FXML
    void iniciarSesion(ActionEvent event) {
        Usuario usuario = new Usuario(txtUsuario.getText(), txtPassword.getText());
        UsuarioValidador validador = new UsuarioValidador();
        ResultadoValidacion resultado = validador.validar(usuario);

        if (!resultado.isValido()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", resultado.getMensaje());
            return;
        }

        if (usuario.getUsername().equals("admin") && usuario.getPassword().equals("1234")) {
            try {
                SceneManager.cambiarVentana(btnIniciar,
                        "/org/example/caso_practico_eventos_navegacion_pasodatos/MenuPrincipal-view.fxml",
                        "Menú Principal - Sistema de Solicitudes");
            } catch (IOException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista del menú principal.");
            }
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Credenciales incorrectas.");
        }
    }

    @FXML
    void salir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar salida");
        alert.setHeaderText("¿Está seguro que desea salir?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
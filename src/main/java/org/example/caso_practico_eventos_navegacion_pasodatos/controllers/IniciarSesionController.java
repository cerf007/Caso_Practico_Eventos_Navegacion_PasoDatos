package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.caso_practico_eventos_navegacion_pasodatos.models.Usuario;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.ResultadoValidacion;
import org.example.caso_practico_eventos_navegacion_pasodatos.util.UsuarioValidador;

import java.io.IOException;
import java.util.Optional;

public class IniciarSesionController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnIniciar;

    @FXML
    public void initialize() {
        txtPassword.setOnKeyPressed(this::manejarTeclado);
    }

    private void manejarTeclado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            iniciarSesion(new ActionEvent());
        }
    }

    @FXML
    void iniciarSesion(ActionEvent event) {
        Usuario usuario = new Usuario(txtUsuario.getText(), txtPassword.getText());
        UsuarioValidador validador = new UsuarioValidador();
        ResultadoValidacion resultado = validador.validar(usuario);

        if (!resultado.isValido()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validación");
            alert.setHeaderText("Datos incompletos");
            alert.setContentText(resultado.getMensaje());
            alert.showAndWait();
            return;
        }

        if (usuario.getUsername().equals("admin") && usuario.getPassword().equals("1234")) {
            abrirVentanaPrincipal();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Credenciales incorrectas.");
            alert.showAndWait();
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

    private void abrirVentanaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/caso_practico_eventos_navegacion_pasodatos/MenuPrincipal-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Menú Principal - Sistema de Solicitudes");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btnIniciar.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

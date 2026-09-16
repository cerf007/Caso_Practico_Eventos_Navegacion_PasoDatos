package org.example.caso_practico_eventos_navegacion_pasodatos.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

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
        String user = txtUsuario.getText();
        String pass = txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validación");
            alert.setHeaderText("Campos incompletos");
            alert.setContentText("Por favor, ingrese usuario y contraseña.");
            alert.showAndWait();
        } else if (user.equals("admin") && pass.equals("1234")) {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ni/edu/uam/vistas/VentanaPrincipal.fxml"));
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
package org.example.caso_practico_eventos_navegacion_pasodatos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("IniciarSesion-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Inicio de sesion");
        stage.setScene(scene);
        stage.setMinWidth(860);
        stage.setMinHeight(540);
        stage.show();
    }
}

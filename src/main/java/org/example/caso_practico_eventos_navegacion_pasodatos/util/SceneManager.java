package org.example.caso_practico_eventos_navegacion_pasodatos.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class SceneManager {


    public static void abrirVentanaModal(String recurso, String titulo) throws IOException {
        abrirVentanaModal(recurso, titulo, null); // Llama al método principal pasándole null
    }

    public static <T> void abrirVentanaModal(String recurso, String titulo, Consumer<T> inicializador) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(cargarFxml(recurso, inicializador)));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    public static void cambiarVentana(Node nodoActual, String recurso, String titulo) throws IOException {
        cambiarVentana(nodoActual, recurso, titulo, null);
    }


    public static <T> void cambiarVentana(Node nodoActual, String recurso, String titulo, Consumer<T> inicializador) throws IOException {
        Stage nuevoStage = new Stage();
        nuevoStage.setTitle(titulo);
        nuevoStage.setScene(new Scene(cargarFxml(recurso, inicializador)));
        nuevoStage.show();

        cerrarVentana(nodoActual);
    }

    private static <T> Parent cargarFxml(String recurso, Consumer<T> inicializador) throws IOException {
        var url = SceneManager.class.getResource(recurso);
        if (url == null) {
            throw new IOException("FXML no encontrado: " + recurso);
        }

        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        // Si mandaron datos en el Consumer, se los inyectamos al controlador
        if (inicializador != null) {
            T controlador = loader.getController();
            if (controlador != null) {
                inicializador.accept(controlador);
            }
        }
        return root;
    }

    public static void cerrarVentana(Node nodo) {
        if (nodo != null && nodo.getScene() != null && nodo.getScene().getWindow() != null) {
            Stage stage = (Stage) nodo.getScene().getWindow();
            stage.close();
        }
    }
}
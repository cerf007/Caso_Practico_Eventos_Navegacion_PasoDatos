module org.example.caso_practico_eventos_navegacion_pasodatos {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.caso_practico_eventos_navegacion_pasodatos to javafx.fxml;
    exports org.example.caso_practico_eventos_navegacion_pasodatos;
}
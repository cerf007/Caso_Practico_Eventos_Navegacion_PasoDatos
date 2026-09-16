module org.example.caso_practico_eventos_navegacion_pasodatos {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens org.example.caso_practico_eventos_navegacion_pasodatos to javafx.fxml;
    exports org.example.caso_practico_eventos_navegacion_pasodatos;
    exports org.example.caso_practico_eventos_navegacion_pasodatos.controllers;
    opens org.example.caso_practico_eventos_navegacion_pasodatos.controllers to javafx.fxml;
}
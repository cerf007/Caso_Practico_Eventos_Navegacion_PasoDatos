package org.example.caso_practico_eventos_navegacion_pasodatos.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private String username;
    private String password;
}
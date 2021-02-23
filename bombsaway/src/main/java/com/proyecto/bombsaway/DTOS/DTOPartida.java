package com.proyecto.bombsaway.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTOPartida {
    private final String nombrePartida;
    private final String nombreUsuarioUno;
    private final String nombreUsuarioDos;

    public DTOPartida(@JsonProperty("nombreUsuarioUno") String nombreUsuarioUno,
                      @JsonProperty("nombreUsuarioDos") String nombreUsuarioDos,
                      @JsonProperty("nombrePartida") String nombrePartida) {
        this.nombreUsuarioUno = nombreUsuarioUno;
        this.nombreUsuarioDos = nombreUsuarioDos;
        this.nombrePartida = nombrePartida;
    }

    public String getNombrePartida() {
        return nombrePartida;
    }

    public String getNombreUsuarioUno() {
        return nombreUsuarioUno;
    }

    public String getNombreUsuarioDos() {
        return nombreUsuarioDos;
    }
}

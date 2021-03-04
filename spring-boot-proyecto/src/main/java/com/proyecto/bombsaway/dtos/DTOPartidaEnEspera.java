package com.proyecto.bombsaway.dtos;

public class DTOPartidaEnEspera {
    private String nombrePartida;
    private String nombreJugador;

    public DTOPartidaEnEspera() {}

    public DTOPartidaEnEspera(String nombrePartida, String nombreJugador) {
        this.nombrePartida = nombrePartida;
        this.nombreJugador = nombreJugador;
    }

    public void setNombrePartida(String nombrePartida) {
        this.nombrePartida = nombrePartida;
    }

    public void setNombreUsuario(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getNombrePartida() {
        return nombrePartida;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }
}

package com.proyecto.bombsaway.clases;

public class PartidaEnEspera {
    private String nombrePartida;
    private String nombreJugador;

    public PartidaEnEspera(){}

    public PartidaEnEspera(String nombrePartida, String nombreJugador) {
        this.nombrePartida = nombrePartida;
        this.nombreJugador = nombreJugador;
    }

    public String getNombrePartida() {
        return nombrePartida;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombrePartida(String nombrePartida) {
        this.nombrePartida = nombrePartida;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
}

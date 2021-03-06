package com.proyecto.bombsaway.clases;

import com.proyecto.bombsaway.dtos.DTOUsuario;
import org.springframework.beans.factory.annotation.Autowired;

public class Partida {
    private String nombre;
    private Jugador jugadorUno;
    private Jugador jugadorDos;
    private boolean finalizada;

    @Autowired
    public Partida(Jugador jugadorUno, Jugador jugadorDos, String nombrePartida, boolean finalizada) {
        this.nombre = nombrePartida;
        this.jugadorUno = jugadorUno;
        this.jugadorDos = jugadorDos;
        this.finalizada = finalizada;
    }

    public String getNombre() {
        return nombre;
    }

    public Jugador getJugadorUno() {
        return jugadorUno;
    }

    public Jugador getJugadorDos() {
        return jugadorDos;
    }

    @Override
    public String toString() {
        String res = "Partida: [" + this.getNombre() + ", " + this.jugadorUno.toString() + ", " +
                this.jugadorDos.toString() + ", " + this.finalizada + " ]";
        return res;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setJugadorUno(Jugador jugadorUno) {
        this.jugadorUno = jugadorUno;
    }

    public void setJugadorDos(Jugador jugadorDos) {
        this.jugadorDos = jugadorDos;
    }

	public boolean isFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}
}

package com.proyecto.bombsaway.clases;

public class PartidaCargada {

	private String nombre;

	private Jugador jugadorUno;

	private Jugador jugadorDos;

	private boolean jugadorUnoListo = false;

	private boolean jugadorDosListo = false;

	public PartidaCargada() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Jugador getJugadorUno() {
		return jugadorUno;
	}

	public void setJugadorUno(Jugador jugadorUno) {
		this.jugadorUno = jugadorUno;
	}

	public Jugador getJugadorDos() {
		return jugadorDos;
	}

	public void setJugadorDos(Jugador jugadorDos) {
		this.jugadorDos = jugadorDos;
	}

	public boolean isJugadorUnoListo() {
		return jugadorUnoListo;
	}

	public void setJugadorUnoListo(boolean jugadorUnoListo) {
		this.jugadorUnoListo = jugadorUnoListo;
	}

	public boolean isJugadorDosListo() {
		return jugadorDosListo;
	}

	public void setJugadorDosListo(boolean jugadorDosListo) {
		this.jugadorDosListo = jugadorDosListo;
	}

}

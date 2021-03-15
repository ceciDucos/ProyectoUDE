package com.proyecto.bombsaway.dtos;

public class DTOCargarPartida {

	private String nombrePartida;

	private String nombreJugador;

	public DTOCargarPartida() {
	}

	public DTOCargarPartida(String nombrePartida, String nombreJugador) {
		this.nombrePartida = nombrePartida;
		this.nombreJugador = nombreJugador;
	}

	public String getNombrePartida() {
		return nombrePartida;
	}

	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}

	public String getNombreJugador() {
		return nombreJugador;
	}

	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}
}

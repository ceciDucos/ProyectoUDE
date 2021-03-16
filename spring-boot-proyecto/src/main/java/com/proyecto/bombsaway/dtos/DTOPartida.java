package com.proyecto.bombsaway.dtos;

public class DTOPartida {
	private String nombrePartida;
	private String nombreJugadorUno;
	private String nombreJugadorDos;

	public DTOPartida() {
	}

	public DTOPartida(String nombreJugadorUno, String nombreJugadorDos, String nombrePartida) {
		this.nombreJugadorUno = nombreJugadorUno;
		this.nombreJugadorDos = nombreJugadorDos;
		this.nombrePartida = nombrePartida;
	}

	public String getNombrePartida() {
		return nombrePartida;
	}

	public String getNombreJugadorUno() {
		return nombreJugadorUno;
	}

	public String getNombreJugadorDos() {
		return nombreJugadorDos;
	}
}

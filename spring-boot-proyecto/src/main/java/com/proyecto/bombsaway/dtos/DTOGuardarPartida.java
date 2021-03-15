package com.proyecto.bombsaway.dtos;

public class DTOGuardarPartida {

	private String nombrePartida;

	private int idJugador;

	public DTOGuardarPartida() {
	}

	public DTOGuardarPartida(String nombrePartida, int idJugador) {
		this.nombrePartida = nombrePartida;
		this.idJugador = idJugador;
	}

	public String getNombrePartida() {
		return nombrePartida;
	}

	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}

	public int getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

}

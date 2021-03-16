package com.proyecto.bombsaway.dtos;

public class DTOGuardarPartida {

	private String nombrePartida;

	private int idJugadorGuarda;

	public DTOGuardarPartida() {
	}

	public DTOGuardarPartida(String nombrePartida, int idJugadorGuarda) {
		this.nombrePartida = nombrePartida;
		this.idJugadorGuarda = idJugadorGuarda;
	}

	public String getNombrePartida() {
		return nombrePartida;
	}

	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}

	public int getIdJugadorGuarda() {
		return idJugadorGuarda;
	}

	public void setIdJugadorGuarda(int idJugadorGuarda) {
		this.idJugadorGuarda = idJugadorGuarda;
	}

}

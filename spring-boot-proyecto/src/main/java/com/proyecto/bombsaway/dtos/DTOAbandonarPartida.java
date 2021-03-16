package com.proyecto.bombsaway.dtos;

public class DTOAbandonarPartida {

	private String nombrePartida;

	private int idJugadorAbandona;

	public DTOAbandonarPartida() {
	}

	public DTOAbandonarPartida(String nombrePartida, int idJugadorAbandona) {
		this.nombrePartida = nombrePartida;
		this.idJugadorAbandona = idJugadorAbandona;
	}

	public String getNombrePartida() {
		return nombrePartida;
	}

	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}

	public int getIdJugadorAbandona() {
		return idJugadorAbandona;
	}

	public void setIdJugadorAbandona(int idJugadorAbandona) {
		this.idJugadorAbandona = idJugadorAbandona;
	}

}

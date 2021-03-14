package com.proyecto.bombsaway.dtos;

public class DTOResultadoPartida {
	private String nombrePartida;
	private boolean jugadorUnoGano;
	private boolean jugadorDosGano;

	public DTOResultadoPartida() {
	}

	public DTOResultadoPartida(String nombrePartida, boolean jugadorUnoGano, boolean jugadorDosGano) {
		this.nombrePartida = nombrePartida;
		this.jugadorUnoGano = jugadorUnoGano;
		this.jugadorDosGano = jugadorDosGano;
	}

	public String getNombrePartida() {
		return nombrePartida;
	}

	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}

	public boolean isJugadorUnoGano() {
		return jugadorUnoGano;
	}

	public void setJugadorUnoGano(boolean jugadorUnoGano) {
		this.jugadorUnoGano = jugadorUnoGano;
	}

	public boolean isJugadorDosGano() {
		return jugadorDosGano;
	}

	public void setJugadorDosGano(boolean jugadorDosGano) {
		this.jugadorDosGano = jugadorDosGano;
	}

	@Override
	public String toString() {
		String response = "{\"nombrePartida\":\"" + this.nombrePartida + "\",\"jugadorUnoGano\":" + this.jugadorUnoGano
				+ ",\"jugadorDosGano\":" + this.jugadorDosGano + "}";
		return response;
	}

}

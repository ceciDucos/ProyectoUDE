package com.proyecto.bombsaway.dtos;

public class DTOBomba {

	private String nombrePartida;

	private int idJugador;

	private int ejeX;
	
	private int ejeY;
	
	private int avionId;
	
	public DTOBomba() {}

	public DTOBomba(String nombrePartida, int idJugador, int ejeX, int ejeY, int avionId) {
		this.nombrePartida = nombrePartida;
		this.idJugador = idJugador;
		this.ejeX = ejeX;
		this.ejeY = ejeY;
		this.avionId = avionId;
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

	public int getEjeX() {
		return ejeX;
	}

	public void setEjeX(int ejeX) {
		this.ejeX = ejeX;
	}

	public int getEjeY() {
		return ejeY;
	}

	public void setEjeY(int ejeY) {
		this.ejeY = ejeY;
	}

	public int getAvionId() {
		return avionId;
	}

	public void setAvionId(int avionId) {
		this.avionId = avionId;
	}
	
}

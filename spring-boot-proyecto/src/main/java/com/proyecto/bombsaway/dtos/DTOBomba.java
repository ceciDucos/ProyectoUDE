package com.proyecto.bombsaway.dtos;

public class DTOBomba {
	private String nombrePartida;
	private int idJugador;
	private double ejeX;
	private double ejeY;
	private int avionId;
	
	public DTOBomba() {}

	public DTOBomba(String nombrePartida, int idJugador, double ejeX, double ejeY, int avionId) {
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

	public double getEjeX() {
		return ejeX;
	}

	public void setEjeX(double ejeX) {
		this.ejeX = ejeX;
	}

	public double getEjeY() {
		return ejeY;
	}

	public void setEjeY(double ejeY) {
		this.ejeY = ejeY;
	}

	public int getAvionId() {
		return avionId;
	}

	public void setAvionId(int avionId) {
		this.avionId = avionId;
	}
	
}

package com.proyecto.bombsaway.dtos;

public class DTOArtilleria {
	private String nombrePartida;
	private int idJugador;
	private int idArtilleria;
	private int ejeX;
	private int ejeY;
	private int angulo;
	private boolean destruida;

	public DTOArtilleria() {
	}

	public DTOArtilleria(String nombrePartida, int idJugador, int idArtilleria, int ejeX, int ejeY, int angulo,
			boolean destruida) {
		super();
		this.nombrePartida = nombrePartida;
		this.idJugador = idJugador;
		this.idArtilleria = idArtilleria;
		this.ejeX = ejeX;
		this.ejeY = ejeY;
		this.angulo = angulo;
		this.destruida = destruida;
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

	public int getIdArtilleria() {
		return idArtilleria;
	}

	public void setIdArtilleria(int idArtilleria) {
		this.idArtilleria = idArtilleria;
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

	public int getAngulo() {
		return angulo;
	}

	public void setAngulo(int angulo) {
		this.angulo = angulo;
	}

	public boolean isDestruida() {
		return destruida;
	}

	public void setDestruida(boolean destruida) {
		this.destruida = destruida;
	}

	@Override
	public String toString() {
		String response = "{\"nombrePartida\":\"" + this.nombrePartida + "\",\"idJugador\":" + this.idJugador
				+ ",\"idArtilleria\":" + this.idArtilleria + ",\"ejeX\":" + this.ejeX + ",\"ejeY\":" + this.ejeY
				+ ",\"angulo\":" + this.angulo + ",\"destruida\":" + this.destruida + "}";
		return response;
	}
}

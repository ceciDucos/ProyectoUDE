package com.proyecto.bombsaway.dtos;

public class DTOEstadoBase {

	private String nombrePartida;

	private int idJugador;

	private boolean hangarDestruido;

	private boolean tanqueCombustibleDestruido;

	private boolean torretaDestruida;

	public DTOEstadoBase() {
	}

	public DTOEstadoBase(String nombrePartida, int idJugador, boolean hangarDestruido,
			boolean tanqueCombustibleDestruido, boolean torretaDestruida) {
		this.nombrePartida = nombrePartida;
		this.idJugador = idJugador;
		this.hangarDestruido = hangarDestruido;
		this.tanqueCombustibleDestruido = tanqueCombustibleDestruido;
		this.torretaDestruida = torretaDestruida;
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

	public boolean isHangarDestruido() {
		return hangarDestruido;
	}

	public void setHangarDestruido(boolean hangarDestruido) {
		this.hangarDestruido = hangarDestruido;
	}

	public boolean isTanqueCombustibleDestruido() {
		return tanqueCombustibleDestruido;
	}

	public void setTanqueCombustibleDestruido(boolean tanqueCombustibleDestruido) {
		this.tanqueCombustibleDestruido = tanqueCombustibleDestruido;
	}

	public boolean isTorretaDestruida() {
		return torretaDestruida;
	}

	public void setTorretaDestruida(boolean torretaDestruida) {
		this.torretaDestruida = torretaDestruida;
	}

	@Override
	public String toString() {
		String response = "{\"nombrePartida\":\"" + this.nombrePartida + "\",\"idJugador\":" + this.idJugador
				+ ",\"hangarDestruido\":" + this.hangarDestruido + ",\"tanqueCombustibleDestruido\":"
				+ this.tanqueCombustibleDestruido + ",\"torretaDestruida\":" + this.torretaDestruida + "}";
		return response;
	}

}

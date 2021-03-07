package com.proyecto.bombsaway.dtos;

public class DTOBase {

	private String nombrePartida;

	private int idJugador;

	private int baseEjeX;
	
	private int baseEjeY;
	
	private int hangarEjeX;
	
	private int hangarEjeY;
	
	private int torretaEjeX;
	
	private int torretaEjeY;
	
	private int tanqueCombustibleEjeX;
	
	private int tanqueCombustibleEjeY;

	public DTOBase() {}

	public DTOBase(String nombrePartida, int idJugador, int baseEjeX, int baseEjeY, int hangarEjeX, int hangarEjeY,
			int torretaEjeX, int torretaEjeY, int tanqueCombustibleEjeX, int tanqueCombustibleEjeY) {
		super();
		this.nombrePartida = nombrePartida;
		this.idJugador = idJugador;
		this.baseEjeX = baseEjeX;
		this.baseEjeY = baseEjeY;
		this.hangarEjeX = hangarEjeX;
		this.hangarEjeY = hangarEjeY;
		this.torretaEjeX = torretaEjeX;
		this.torretaEjeY = torretaEjeY;
		this.tanqueCombustibleEjeX = tanqueCombustibleEjeX;
		this.tanqueCombustibleEjeY = tanqueCombustibleEjeY;
	}

	public DTOBase(int baseEjeX, int baseEjeY, int hangarEjeX, int hangarEjeY, int torretaEjeX, int torretaEjeY,
			int tanqueCombustibleEjeX, int tanqueCombustibleEjeY) {
		super();
		this.baseEjeX = baseEjeX;
		this.baseEjeY = baseEjeY;
		this.hangarEjeX = hangarEjeX;
		this.hangarEjeY = hangarEjeY;
		this.torretaEjeX = torretaEjeX;
		this.torretaEjeY = torretaEjeY;
		this.tanqueCombustibleEjeX = tanqueCombustibleEjeX;
		this.tanqueCombustibleEjeY = tanqueCombustibleEjeY;
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

	public int getBaseEjeX() {
		return baseEjeX;
	}

	public void setBaseEjeX(int baseEjeX) {
		this.baseEjeX = baseEjeX;
	}

	public int getBaseEjeY() {
		return baseEjeY;
	}

	public void setBaseEjeY(int baseEjeY) {
		this.baseEjeY = baseEjeY;
	}

	public int getHangarEjeX() {
		return hangarEjeX;
	}

	public void setHangarEjeX(int hangarEjeX) {
		this.hangarEjeX = hangarEjeX;
	}

	public int getHangarEjeY() {
		return hangarEjeY;
	}

	public void setHangarEjeY(int hangarEjeY) {
		this.hangarEjeY = hangarEjeY;
	}

	public int getTorretaEjeX() {
		return torretaEjeX;
	}

	public void setTorretaEjeX(int torretaEjeX) {
		this.torretaEjeX = torretaEjeX;
	}

	public int getTorretaEjeY() {
		return torretaEjeY;
	}

	public void setTorretaEjeY(int torretaEjeY) {
		this.torretaEjeY = torretaEjeY;
	}

	public int getTanqueCombustibleEjeX() {
		return tanqueCombustibleEjeX;
	}

	public void setTanqueCombustibleEjeX(int tanqueCombustibleEjeX) {
		this.tanqueCombustibleEjeX = tanqueCombustibleEjeX;
	}

	public int getTanqueCombustibleEjeY() {
		return tanqueCombustibleEjeY;
	}

	public void setTanqueCombustibleEjeY(int tanqueCombustibleEjeY) {
		this.tanqueCombustibleEjeY = tanqueCombustibleEjeY;
	}

	@Override
	public String toString() {
		return "{\"nombrePartida\":\""+ nombrePartida +"\",\"idJugador\":"+ idJugador +",\"baseEjeX\":"+ baseEjeX
				+ ",\"baseEjeY\":" + baseEjeY + ",\"hangarEjeX\":" + hangarEjeX + ",\"hangarEjeY\":" + hangarEjeY
				+ ",\"torretaEjeX\":" + torretaEjeX + ",\"torretaEjeY\":" + torretaEjeY + ",\"tanqueCombustibleEjeX\":"
				+ tanqueCombustibleEjeX + ",\"tanqueCombustibleEjeY\":" + tanqueCombustibleEjeY + "}";
	}
	
}


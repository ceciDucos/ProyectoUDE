package com.proyecto.bombsaway.dtos;

public class DTOBase {
	private String nombrePartida;
	private int idJugador;
	private double baseEjeX;
	private double baseEjeY;
	private double hangarEjeX;
	private double hangarEjeY;
	private double torretaEjeX;
	private double torretaEjeY;
	private double tanqueCombustibleEjeX;
	private double tanqueCombustibleEjeY;

	public DTOBase() {}

	public DTOBase(String nombrePartida, int idJugador, double baseEjeX, double baseEjeY, double hangarEjeX, double hangarEjeY,
				   double torretaEjeX, double torretaEjeY, double tanqueCombustibleEjeX, double tanqueCombustibleEjeY) {
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

	public DTOBase(double baseEjeX, double baseEjeY, double hangarEjeX, double hangarEjeY, double torretaEjeX, double torretaEjeY,
				   double tanqueCombustibleEjeX, double tanqueCombustibleEjeY) {
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

	public double getBaseEjeX() {
		return baseEjeX;
	}

	public void setBaseEjeX(double baseEjeX) {
		this.baseEjeX = baseEjeX;
	}

	public double getBaseEjeY() {
		return baseEjeY;
	}

	public void setBaseEjeY(double baseEjeY) {
		this.baseEjeY = baseEjeY;
	}

	public double getHangarEjeX() {
		return hangarEjeX;
	}

	public void setHangarEjeX(double hangarEjeX) {
		this.hangarEjeX = hangarEjeX;
	}

	public double getHangarEjeY() {
		return hangarEjeY;
	}

	public void setHangarEjeY(double hangarEjeY) {
		this.hangarEjeY = hangarEjeY;
	}

	public double getTorretaEjeX() {
		return torretaEjeX;
	}

	public void setTorretaEjeX(double torretaEjeX) {
		this.torretaEjeX = torretaEjeX;
	}

	public double getTorretaEjeY() {
		return torretaEjeY;
	}

	public void setTorretaEjeY(double torretaEjeY) {
		this.torretaEjeY = torretaEjeY;
	}

	public double getTanqueCombustibleEjeX() {
		return tanqueCombustibleEjeX;
	}

	public void setTanqueCombustibleEjeX(double tanqueCombustibleEjeX) {
		this.tanqueCombustibleEjeX = tanqueCombustibleEjeX;
	}

	public double getTanqueCombustibleEjeY() {
		return tanqueCombustibleEjeY;
	}

	public void setTanqueCombustibleEjeY(double tanqueCombustibleEjeY) {
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


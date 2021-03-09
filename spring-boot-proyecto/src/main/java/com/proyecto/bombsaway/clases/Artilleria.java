package com.proyecto.bombsaway.clases;

public class Artilleria {

	private int idArtilleria;
	
	private Posicion posicion;
	
	private boolean destruida;
	
	public Artilleria() {}

	public Artilleria(int idArtilleria, Posicion posicion, boolean destruida) {
		this.idArtilleria = idArtilleria;
		this.posicion = posicion;
		this.destruida = destruida;
	}

	public int getIdArtilleria() {
		return idArtilleria;
	}

	public void setIdArtilleria(int idArtilleria) {
		this.idArtilleria = idArtilleria;
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	public boolean isDestruida() {
		return destruida;
	}

	public void setDestruida(boolean destruida) {
		this.destruida = destruida;
	}
	
}

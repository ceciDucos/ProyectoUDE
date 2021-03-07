package com.proyecto.bombsaway.clases;

public class Artilleria {

	private Posicion posicion;
	
	private boolean destruida;
	
	public Artilleria() {
		
	}

	public Artilleria(Posicion posicion, boolean destruida) {
		this.posicion = posicion;
		this.destruida = destruida;
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

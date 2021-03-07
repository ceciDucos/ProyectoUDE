package com.proyecto.bombsaway.clases;

public class ElementoBase {

	private String nombre;
	
	private Posicion posicion;

	private boolean destruido;

	public ElementoBase() {}

	public ElementoBase(String nombre, Posicion posicion, boolean destruido) {
		super();
		this.nombre = nombre;
		this.posicion = posicion;
		this.destruido = destruido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	public boolean isDestruido() {
		return destruido;
	}

	public void setDestruido(boolean destruido) {
		this.destruido = destruido;
	}	
	
}


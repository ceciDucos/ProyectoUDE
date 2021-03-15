package com.proyecto.bombsaway.clases;

import java.util.ArrayList;
import java.util.List;

public class ElementoBase {
	private String nombre;
	private Posicion posicion;
	private boolean destruido;
	private List<Bala> listBalas;

	public ElementoBase() {
	}

	public ElementoBase(String nombre, Posicion posicion, boolean destruido) {
		super();
		this.nombre = nombre;
		this.posicion = posicion;
		this.destruido = destruido;
	}

	public List<Bala> getListBalas() {
		return listBalas;
	}

	public void setListBalas(List<Bala> listBalas) {
		this.listBalas = listBalas;
	}

	public void inicializarBalas() {
		this.listBalas = new ArrayList<Bala>();
		for (int i = 0; i < 30; i++) {
			Bala balaTorreta = new Bala();
			balaTorreta.setId(i);
			balaTorreta.setPosicion(this.posicion);
			balaTorreta.setVisible(false);
			listBalas.add(balaTorreta);
		}
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

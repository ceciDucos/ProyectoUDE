package com.proyecto.bombsaway.clases;

import java.util.ArrayList;
import java.util.List;

public class Artilleria {
	private int idArtilleria;
	private Posicion posicion;
	private boolean destruida;
	private List<Bala> listBalas;
	private boolean visible;
	
	public Artilleria() {}

	public Artilleria(int idArtilleria, Posicion posicion, boolean destruida) {
		this.idArtilleria = idArtilleria;
		this.posicion = posicion;
		this.destruida = destruida;
	}

	public void inicializarBalas() {
		this.listBalas = new ArrayList<Bala>();
		for (int i = 0; i < 30; i++){
			Bala balaAvion = new Bala();
			balaAvion.setId(i);
			balaAvion.setVisible(false);
			listBalas.add(balaAvion);
		}
	}

	public List<Bala> getListBalas() {
		return listBalas;
	}

	public void setListaBalas(List<Bala> listaBalas) {
		this.listBalas = listBalas;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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

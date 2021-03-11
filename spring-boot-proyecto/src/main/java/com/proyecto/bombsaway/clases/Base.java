package com.proyecto.bombsaway.clases;

import java.util.List;

import com.proyecto.bombsaway.dtos.DTOBase;

public class Base {
	private Posicion posicion;
	private List<ElementoBase> elementosBase;
	private boolean visible;

	public Base() { }

	public Base(Posicion posicion, List<ElementoBase> elementosBase) {
		this.posicion = posicion;
		this.elementosBase = elementosBase;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	public List<ElementoBase> getElementosBase() {
		return elementosBase;
	}

	public void setElementosBase(List<ElementoBase> elementosBase) {
		this.elementosBase = elementosBase;
	}

	public DTOBase getDTO() {
		int baseEjeX = this.posicion.getEjeX();
		int baseEjeY = this.posicion.getEjeY();
		int hangarEjeX = 0;
		int hangarEjeY = 0;
		int torretaEjeX = 0;
		int torretaEjeY = 0;
		int tanqueCombustibleX = 0;
		int tanqueCombustibleY = 0;
		for (ElementoBase elemento : this.elementosBase) {
			if (elemento.getNombre() == "torreta") {
				torretaEjeX = elemento.getPosicion().getEjeX();
				torretaEjeY = elemento.getPosicion().getEjeY();
			} else if (elemento.getNombre() == "hangar") {
				hangarEjeX = elemento.getPosicion().getEjeX();
				hangarEjeY = elemento.getPosicion().getEjeY();
			} else if (elemento.getNombre() == "tanque_combustible") {
				tanqueCombustibleX = elemento.getPosicion().getEjeX();
				tanqueCombustibleY = elemento.getPosicion().getEjeY();
			}
		}
		return new DTOBase(baseEjeX, baseEjeY, hangarEjeX, hangarEjeY, torretaEjeX, torretaEjeY, tanqueCombustibleX,
				tanqueCombustibleY);
	}

}

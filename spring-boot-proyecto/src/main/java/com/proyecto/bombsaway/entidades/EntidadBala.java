package com.proyecto.bombsaway.entidades;

import javax.persistence.*;

@Entity
@Table(name = "balas")
public class EntidadBala {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bala")
	private int id;

	@Column(name = "eje_x", nullable = false)
	private int ejeX;

	@Column(name = "eje_y", nullable = false)
	private int ejeY;

	@Column(name = "angulo", nullable = false)
	private int angulo;

	@Column(name = "visible", nullable = false)
	private boolean visible;

	public EntidadBala() {
	}

	public EntidadBala(int id, int ejeX, int ejeY, int angulo, boolean visible) {
		this.id = id;
		this.ejeX = ejeX;
		this.ejeY = ejeY;
		this.angulo = angulo;
		this.visible = visible;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}

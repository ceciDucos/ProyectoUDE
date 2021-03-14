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
	private double ejeX;

	@Column(name = "eje_y", nullable = false)
	private double ejeY;

	@Column(name = "angulo", nullable = false)
	private double angulo;

	@Column(name = "visible", nullable = false)
	private boolean visible;

	public EntidadBala() {
	}

	public EntidadBala(int id, double ejeX, double ejeY, double angulo, boolean visible) {
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

	public double getEjeX() {
		return ejeX;
	}

	public void setEjeX(double ejeX) {
		this.ejeX = ejeX;
	}

	public double getEjeY() {
		return ejeY;
	}

	public void setEjeY(double ejeY) {
		this.ejeY = ejeY;
	}

	public double getAngulo() {
		return angulo;
	}

	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}

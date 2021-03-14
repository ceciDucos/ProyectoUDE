package com.proyecto.bombsaway.entidades;

import javax.persistence.*;

@Entity
@Table(name = "elementos_bases")
public class EntidadElementoBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_elemento_base")
	private int id;

	@Column(name = "nombre", nullable = false, length = 255)
	private String nombre;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_base")
	private EntidadBase base;

	@Column(name = "eje_x", nullable = false)
	private double ejeX;

	@Column(name = "eje_y", nullable = false)
	private double ejeY;

	@Column(name = "angulo", nullable = false)
	private double angulo;

	@Column(name = "destruido", nullable = false)
	private boolean destruido;

	public EntidadElementoBase() {
	}

	public EntidadElementoBase(int id, String nombre, EntidadBase base, double ejeX, double ejeY, double angulo,
			boolean destruido) {
		this.id = id;
		this.nombre = nombre;
		this.base = base;
		this.ejeX = ejeX;
		this.ejeY = ejeY;
		this.angulo = angulo;
		this.destruido = destruido;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EntidadBase getBase() {
		return base;
	}

	public void setBase(EntidadBase base) {
		this.base = base;
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

	public boolean isDestruido() {
		return destruido;
	}

	public void setDestruido(boolean destruido) {
		this.destruido = destruido;
	}

}

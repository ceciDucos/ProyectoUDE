package com.proyecto.bombsaway.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.proyecto.bombsaway.enumerados.EstadoAvion;

@Entity
@Table(name = "aviones")
public class EntidadAvion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_avion")
	private int id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_jugador")
	private EntidadJugador jugador;

	@Column(name = "eje_x", nullable = false)
	private double ejeX;

	@Column(name = "eje_y", nullable = false)
	private double ejeY;

	@Column(name = "angulo", nullable = false)
	private double angulo;

	@Column(name = "estado", nullable = false)
	@Enumerated(EnumType.STRING)
	private EstadoAvion estado;

	@Column(name = "vida", nullable = false)
	private int vida;

	@Column(name = "combustible", nullable = false)
	private int combustible;

	@Column(name = "tiene_bomba", nullable = false)
	private boolean tieneBomba;

	@Column(name = "visible", nullable = false)
	private boolean visible;

	public EntidadAvion() {
	}

	public EntidadAvion(int id, EntidadJugador jugador, double ejeX, double ejeY, double angulo, EstadoAvion estado,
			int vida, int combustible, boolean tieneBomba, boolean visible) {
		this.id = id;
		this.jugador = jugador;
		this.ejeX = ejeX;
		this.ejeY = ejeY;
		this.angulo = angulo;
		this.estado = estado;
		this.vida = vida;
		this.combustible = combustible;
		this.tieneBomba = tieneBomba;
		this.visible = visible;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EntidadJugador getJugador() {
		return jugador;
	}

	public void setJugador(EntidadJugador jugador) {
		this.jugador = jugador;
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

	public EstadoAvion getEstado() {
		return estado;
	}

	public void setEstado(EstadoAvion estado) {
		this.estado = estado;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getCombustible() {
		return combustible;
	}

	public void setCombustible(int combustible) {
		this.combustible = combustible;
	}

	public boolean isTieneBomba() {
		return tieneBomba;
	}

	public void setTieneBomba(boolean tieneBomba) {
		this.tieneBomba = tieneBomba;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}

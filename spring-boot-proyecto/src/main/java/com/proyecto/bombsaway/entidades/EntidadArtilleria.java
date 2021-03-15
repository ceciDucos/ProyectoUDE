package com.proyecto.bombsaway.entidades;

import javax.persistence.*;

@Entity
@Table(name = "artillerias")
public class EntidadArtilleria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_artilleria")
	private int id;

	@Column(name = "numero", nullable = false)
	private int numero;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_jugador")
	private EntidadJugador jugador;

	@Column(name = "eje_x", nullable = false)
	private double ejeX;

	@Column(name = "eje_y", nullable = false)
	private double ejeY;

	@Column(name = "angulo", nullable = false)
	private double angulo;

	@Column(name = "destruida", nullable = false)
	private boolean destruida;

	@Column(name = "visible", nullable = false)
	private boolean visible;

	public EntidadArtilleria() {
	}

	public EntidadArtilleria(int id, int numero, EntidadJugador jugador, double ejeX, double ejeY, double angulo,
			boolean destruida, boolean visible) {
		this.id = id;
		this.numero = numero;
		this.jugador = jugador;
		this.ejeX = ejeX;
		this.ejeY = ejeY;
		this.angulo = angulo;
		this.destruida = destruida;
		this.visible = visible;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
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

	public boolean isDestruida() {
		return destruida;
	}

	public void setDestruida(boolean destruida) {
		this.destruida = destruida;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}

package com.proyecto.bombsaway.entidades;

import javax.persistence.*;

@Entity
@Table(name = "artillerias")
public class EntidadArtilleria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_artilleria")
	private int id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_jugador")
	private EntidadJugador jugador;

	@Column(name = "eje_x", nullable = false)
	private int ejeX;

	@Column(name = "eje_y", nullable = false)
	private int ejeY;

	@Column(name = "angulo", nullable = false)
	private int angulo;

	@Column(name = "destruida", nullable = false)
	private boolean destruida;

	@Column(name = "visible", nullable = false)
	private boolean visible;

	public EntidadArtilleria() {
	}

	public EntidadArtilleria(int id, EntidadJugador jugador, int ejeX, int ejeY, int angulo, boolean destruida, boolean visible) {
		this.id = id;
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

	public EntidadJugador getJugador() {
		return jugador;
	}

	public void setJugador(EntidadJugador jugador) {
		this.jugador = jugador;
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

package com.proyecto.bombsaway.entidades;

import javax.persistence.*;

@Entity
@Table(name = "partidas")
public class EntidadPartida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_partida")
	private int id;

	@Column(name = "nombre", nullable = false, length = 255)
	private String nombre;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_jugador_1", referencedColumnName = "id_jugador")
	private EntidadJugador jugador1;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_jugador_2", referencedColumnName = "id_jugador")
	private EntidadJugador jugador2;

	@Column(name = "finalizada", nullable = false)
	private boolean finalizada;

	public EntidadPartida() {
	}

	public EntidadPartida(int id, String nombre, EntidadJugador jugador1, EntidadJugador jugador2, boolean finalizada) {
		this.id = id;
		this.nombre = nombre;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.finalizada = finalizada;
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

	public EntidadJugador getJugador1() {
		return jugador1;
	}

	public void setJugador1(EntidadJugador jugador1) {
		this.jugador1 = jugador1;
	}

	public EntidadJugador getJugador2() {
		return jugador2;
	}

	public void setJugador2(EntidadJugador jugador2) {
		this.jugador2 = jugador2;
	}

	public boolean isFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}

}

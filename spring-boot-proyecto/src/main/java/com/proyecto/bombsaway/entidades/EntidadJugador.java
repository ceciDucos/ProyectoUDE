package com.proyecto.bombsaway.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jugadores")
public class EntidadJugador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_jugador")
	private int id;

	@Column(name = "numero", nullable = false)
	private int numero;

	@Column(name = "nombre", nullable = false, length = 255)
	private String nombre;

	public EntidadJugador() {
	}

	public EntidadJugador(int id, int numero, String nombre) {
		this.id = id;
		this.numero = numero;
		this.nombre = nombre;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}

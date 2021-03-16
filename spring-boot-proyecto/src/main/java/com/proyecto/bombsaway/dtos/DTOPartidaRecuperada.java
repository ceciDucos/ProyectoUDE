package com.proyecto.bombsaway.dtos;

import java.util.List;

import com.proyecto.bombsaway.clases.Artilleria;
import com.proyecto.bombsaway.clases.Avion;
import com.proyecto.bombsaway.clases.Bala;
import com.proyecto.bombsaway.clases.Base;
import com.proyecto.bombsaway.clases.ElementoBase;
import com.proyecto.bombsaway.clases.Jugador;
import com.proyecto.bombsaway.clases.Posicion;

public class DTOPartidaRecuperada {

	private String nombrePartida;

	private Jugador jugador1;

	private Jugador jugador2;

	public DTOPartidaRecuperada() {
	}

	public DTOPartidaRecuperada(String nombrePartida, Jugador jugador1, Jugador jugador2) {
		this.nombrePartida = nombrePartida;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
	}

	public String getNombrePartida() {
		return nombrePartida;
	}

	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}

	public Jugador getJugador1() {
		return jugador1;
	}

	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}

	public Jugador getJugador2() {
		return jugador2;
	}

	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}

	@Override
	public String toString() {
		String response = "{\"nombrePartida\":\"" + this.nombrePartida + "\", \"jugador1\":\""
				+ this.toStringJugador(this.jugador1) + "\", \"jugadorDos\":\"" + this.toStringJugador(this.jugador2)
				+ "\"}";
		return response;
	}

	public String toStringJugador(Jugador jugador) {
		/*String data = 
				"{\"idJugador\": " + jugador.getId() + 
				", \"nombreJugador\": \"" + jugador.getNombre() + 
				"\", }";
		return data;*/
		return "";
	}

	// nombrePartida
	// jugador1
	// private int id;
	// private String nombre;
	// private List<Avion> listAviones;
	// private Base base
	// private Posicion posicion;
	// private double ejeX;
	// private double ejeY;
	// private double angulo;
	// private List<ElementoBase> elementosBase;
	// private String nombre;
	// private Posicion posicion;
	// private boolean destruido;
	// private List<Bala> listBalas;
	// private boolean visible;
	// private List<Artilleria> listArtilleria;
	// jugador2

}

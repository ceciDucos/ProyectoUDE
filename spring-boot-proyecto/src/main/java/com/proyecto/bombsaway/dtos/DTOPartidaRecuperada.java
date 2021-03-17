package com.proyecto.bombsaway.dtos;

import java.util.List;

import com.proyecto.bombsaway.clases.Artilleria;
import com.proyecto.bombsaway.clases.Avion;
import com.proyecto.bombsaway.clases.Base;
import com.proyecto.bombsaway.clases.ElementoBase;
import com.proyecto.bombsaway.clases.Jugador;

public class DTOPartidaRecuperada {

	private String nombrePartida;

	private Jugador jugador1;

	private Jugador jugador2;

	private int jugadorCreo;

	private int maxArtilleria;

	private boolean error;

	private String mensajeError;

	public DTOPartidaRecuperada() {
	}

	public DTOPartidaRecuperada(String nombrePartida, Jugador jugador1, Jugador jugador2, int jugadorCreo,
			int maxArtilleria, boolean error, String mensajeError) {
		this.nombrePartida = nombrePartida;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.jugadorCreo = jugadorCreo;
		this.maxArtilleria = maxArtilleria;
		this.error = error;
		this.mensajeError = mensajeError;
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

	public int getJugadorCreo() {
		return jugadorCreo;
	}

	public void setJugadorCreo(int jugadorCreo) {
		this.jugadorCreo = jugadorCreo;
	}

	public int getMaxArtilleria() {
		return maxArtilleria;
	}

	public void setMaxArtilleria(int maxArtilleria) {
		this.maxArtilleria = maxArtilleria;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	@Override
	public String toString() {
		String response = "{\"accion\": \"Bootloader\", \"nombrePartida\":\"" + this.nombrePartida
				+ "\", \"maxArtilleria\": " + this.maxArtilleria
				+ ", \"cargarPartida\": true, \"unirsePartida\": false, \"errorMensaje\": \"" + this.mensajeError
				+ "\", \"jugadorUno\": " + this.toStringJugador(this.jugador1) + ", \"jugadorDos\": "
				+ this.toStringJugador(this.jugador2) + "\", \"error\": " + this.error + "}";
		return response;
	}

	public String toStringJugador(Jugador jugador) {
		String data = "{\"idJugador\": " + jugador.getId() + ", \"nombreJugador\": \"" + jugador.getNombre()
				+ "\", \"listAviones\" : " + this.toStringAviones(jugador.getListAviones()) + ", \"listArtillerias\": "
				+ this.toStringArtillerias(jugador) + ", \"baseJugador\": " + jugador.getBase().getDTO().toString()
				+ ", \"listElementosBase\": " + this.toStringElementosBase(jugador.getBase()) + "}";
		return data;
	}

	public String toStringAviones(List<Avion> aviones) {
		String data = "[";
		int i = 0;
		while (i < aviones.size()) {
			data += aviones.get(i).getDTO().toString();
			i++;
			if (i < aviones.size()) {
				data += ",";
			}
		}
		data += "]";
		return data;
	}

	public String toStringArtillerias(Jugador jugador) {
		String data = "[";
		int i = 0;
		while (i < jugador.getListArtilleria().size()) {
			Artilleria artilleria = jugador.getListArtilleria().get(i);
			data += new DTOArtilleria(this.nombrePartida, jugador.getId(), artilleria.getIdArtilleria(),
					artilleria.getPosicion().getEjeX(), artilleria.getPosicion().getEjeY(),
					artilleria.getPosicion().getAngulo(), artilleria.isDestruida()).toString();
			i++;
			if (i < jugador.getListArtilleria().size()) {
				data += ",";
			}
		}
		data += "]";
		return data;
	}

	public String toStringElementosBase(Base base) {
		String data = "[";
		int i = 0;
		while (i < base.getElementosBase().size()) {
			ElementoBase elemento = base.getElementosBase().get(i);
			data += "{\"nombre\": \"" + elemento.getNombre() + "\", \"ejeX\": " + elemento.getPosicion().getEjeX()
					+ ", \"ejeY\": " + elemento.getPosicion().getEjeY() + ", \"angulo\": "
					+ elemento.getPosicion().getAngulo() + ", \"destruido\": " + elemento.isDestruido() + "}";
			i++;
			if (i < base.getElementosBase().size()) {
				data += ",";
			}
		}
		data += "]";
		return data;
	}

}

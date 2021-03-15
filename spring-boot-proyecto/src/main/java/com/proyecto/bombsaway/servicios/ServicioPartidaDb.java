package com.proyecto.bombsaway.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.clases.Artilleria;
import com.proyecto.bombsaway.clases.Avion;
import com.proyecto.bombsaway.clases.Base;
import com.proyecto.bombsaway.clases.ElementoBase;
import com.proyecto.bombsaway.clases.Partida;
import com.proyecto.bombsaway.daos.IDAOPartida;
import com.proyecto.bombsaway.entidades.EntidadArtilleria;
import com.proyecto.bombsaway.entidades.EntidadAvion;
import com.proyecto.bombsaway.entidades.EntidadBase;
import com.proyecto.bombsaway.entidades.EntidadElementoBase;
import com.proyecto.bombsaway.entidades.EntidadJugador;
import com.proyecto.bombsaway.entidades.EntidadPartida;

@Service
public class ServicioPartidaDb {

	@Autowired
	private IDAOPartida daoPartida;

	@Autowired
	private ServicioJugador servicioJugador;

	@Autowired
	private ServicioBase servicioBase;

	@Autowired
	private ServicioElementoBase servicioElementoBase;

	@Autowired
	private ServicioAvion servicioAvion;

	@Autowired
	private ServicioArtilleria servicioArtilleria;

	public void guardarPartida(Partida partida) {
		try {
			if (partida != null) {
				EntidadJugador jugador1 = null;
				EntidadJugador jugador2 = null;
				jugador1 = this.servicioJugador.guardar(partida.getJugadorUno());
				jugador2 = this.servicioJugador.guardar(partida.getJugadorDos());
				if (jugador1 != null && jugador2 != null) {
					EntidadPartida partidaDb = null;
					partidaDb = this.daoPartida.save(
							new EntidadPartida(0, partida.getNombre(), jugador1, jugador2, partida.isFinalizada()));
					if (partidaDb != null) {
						EntidadBase base1 = null;
						EntidadBase base2 = null;
						Base baseJ1 = partida.getJugadorUno().getBase();
						Base baseJ2 = partida.getJugadorDos().getBase();
						base1 = this.servicioBase.guardar(baseJ1, jugador1);
						base2 = this.servicioBase.guardar(baseJ2, jugador2);
						if (base1 != null && base2 != null) {
							for (ElementoBase elementoBaseJ1 : baseJ1.getElementosBase()) {
								this.servicioElementoBase.guardar(elementoBaseJ1, base1);
							}
							for (ElementoBase elementoBaseJ2 : baseJ2.getElementosBase()) {
								this.servicioElementoBase.guardar(elementoBaseJ2, base2);
							}
							for (Avion avionJ1 : partida.getJugadorUno().getListAviones()) {
								this.servicioAvion.guardar(avionJ1, jugador1);
							}
							for (Avion avionJ2 : partida.getJugadorDos().getListAviones()) {
								this.servicioAvion.guardar(avionJ2, jugador2);
							}
							for (Artilleria artilleriaJ1 : partida.getJugadorUno().getListArtilleria()) {
								this.servicioArtilleria.guardar(artilleriaJ1, jugador1);
							}
							for (Artilleria artilleriaJ2 : partida.getJugadorDos().getListArtilleria()) {
								this.servicioArtilleria.guardar(artilleriaJ2, jugador2);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Partida cargarPartida(String nombre) {
		Partida partida = null;
		try {
			EntidadPartida partidaDb = this.daoPartida.findByNombre(nombre);
			EntidadJugador jugador1Db = partidaDb.getJugador1();
			EntidadJugador jugador2Db = partidaDb.getJugador2();
			EntidadBase baseJ1Db = this.servicioBase.buscarPorIdJugador(jugador1Db.getId());
			EntidadBase baseJ2Db = this.servicioBase.buscarPorIdJugador(jugador2Db.getId());
			List<EntidadElementoBase> elementosBaseJ1Db = this.servicioElementoBase
					.buscarTodosPorIdBase(baseJ1Db.getId());
			List<EntidadElementoBase> elementosBaseJ2Db = this.servicioElementoBase
					.buscarTodosPorIdBase(baseJ2Db.getId());
			List<EntidadAvion> avionesJ1Db = this.servicioAvion.buscarTodosPorIdJugador(jugador1Db.getId());
			List<EntidadAvion> avionesJ2Db = this.servicioAvion.buscarTodosPorIdJugador(jugador2Db.getId());
			List<EntidadArtilleria> artilleriaJ1Db = this.servicioArtilleria
					.buscarTodosPorIdJugador(jugador1Db.getId());
			List<EntidadArtilleria> artilleriaJ2Db = this.servicioArtilleria
					.buscarTodosPorIdJugador(jugador2Db.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return partida;
	}

}

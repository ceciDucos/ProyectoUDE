package com.proyecto.bombsaway.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.clases.Artilleria;
import com.proyecto.bombsaway.clases.Avion;
import com.proyecto.bombsaway.clases.Bala;
import com.proyecto.bombsaway.clases.Base;
import com.proyecto.bombsaway.clases.ElementoBase;
import com.proyecto.bombsaway.clases.Partida;
import com.proyecto.bombsaway.daos.IDAOPartida;
import com.proyecto.bombsaway.entidades.EntidadArtilleria;
import com.proyecto.bombsaway.entidades.EntidadAvion;
import com.proyecto.bombsaway.entidades.EntidadBala;
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

	@Autowired
	private ServicioBala servicioBala;

	@Autowired
	private ServicioBalaAvion servicioBalaAvion;

	@Autowired
	private ServicioBalaArtilleria servicioBalaArtilleria;

	@Autowired
	private ServicioBalaTorreta servicioBalaTorreta;

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
								if (elementoBaseJ1.getNombre() == "torreta") {
									EntidadElementoBase torretaJ1 = this.servicioElementoBase.guardar(elementoBaseJ1,
											base1);
									for (Bala balaJ1 : elementoBaseJ1.getListBalas()) {
										EntidadBala balaDb = this.servicioBala.guardar(balaJ1);
										this.servicioBalaTorreta.guardar(balaDb, torretaJ1);
									}
								} else {
									this.servicioElementoBase.guardar(elementoBaseJ1, base1);
								}
							}
							for (ElementoBase elementoBaseJ2 : baseJ2.getElementosBase()) {
								if (elementoBaseJ2.getNombre() == "torreta") {
									EntidadElementoBase torretaJ2 = this.servicioElementoBase.guardar(elementoBaseJ2,
											base2);
									for (Bala balaJ2 : elementoBaseJ2.getListBalas()) {
										EntidadBala balaDb = this.servicioBala.guardar(balaJ2);
										this.servicioBalaTorreta.guardar(balaDb, torretaJ2);
									}
								} else {
									this.servicioElementoBase.guardar(elementoBaseJ2, base2);
								}
							}
							for (Avion avionJ1 : partida.getJugadorUno().getListAviones()) {
								EntidadAvion avionDb = this.servicioAvion.guardar(avionJ1, jugador1);
								for (Bala balaJ1 : avionJ1.getListBalas()) {
									EntidadBala balaDb = this.servicioBala.guardar(balaJ1);
									this.servicioBalaAvion.guardar(balaDb, avionDb);
								}
							}
							for (Avion avionJ2 : partida.getJugadorDos().getListAviones()) {
								EntidadAvion avionDb = this.servicioAvion.guardar(avionJ2, jugador2);
								for (Bala balaJ2 : avionJ2.getListBalas()) {
									EntidadBala balaDb = this.servicioBala.guardar(balaJ2);
									this.servicioBalaAvion.guardar(balaDb, avionDb);
								}
							}
							for (Artilleria artilleriaJ1 : partida.getJugadorUno().getListArtilleria()) {
								EntidadArtilleria artilleriaDb = this.servicioArtilleria.guardar(artilleriaJ1,
										jugador1);
								for (Bala balaJ1 : artilleriaJ1.getListBalas()) {
									EntidadBala balaDb = this.servicioBala.guardar(balaJ1);
									this.servicioBalaArtilleria.guardar(balaDb, artilleriaDb);
								}
							}
							for (Artilleria artilleriaJ2 : partida.getJugadorDos().getListArtilleria()) {
								EntidadArtilleria artilleriaDb = this.servicioArtilleria.guardar(artilleriaJ2,
										jugador2);
								for (Bala balaJ2 : artilleriaJ2.getListBalas()) {
									EntidadBala balaDb = this.servicioBala.guardar(balaJ2);
									this.servicioBalaArtilleria.guardar(balaDb, artilleriaDb);
								}
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		return partida;
	}

}

package com.proyecto.bombsaway.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.clases.Artilleria;
import com.proyecto.bombsaway.clases.Avion;
import com.proyecto.bombsaway.clases.Base;
import com.proyecto.bombsaway.clases.ElementoBase;
import com.proyecto.bombsaway.clases.Jugador;
import com.proyecto.bombsaway.clases.Partida;
import com.proyecto.bombsaway.clases.Posicion;
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

	public void guardarPartida(Partida partida, int jugadorGuardaPartidaId) {
		try {
			if (this.daoPartida.findByNombre(partida.getNombre()) != null) {
				this.actualizarPartida(partida, jugadorGuardaPartidaId);
			} else {
				this.crearPartida(partida, jugadorGuardaPartidaId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearPartida(Partida partida, int jugadorGuardaPartidaId) {
		try {
			if (partida != null) {
				EntidadJugador jugador1 = null;
				EntidadJugador jugador2 = null;
				jugador1 = this.servicioJugador.guardar(partida.getJugadorUno());
				jugador2 = this.servicioJugador.guardar(partida.getJugadorDos());
				EntidadJugador jugadorGuardaPartida = (jugador1.getNumero() == jugadorGuardaPartidaId) ? jugador1
						: jugador2;
				if (jugador1 != null && jugador2 != null) {
					EntidadPartida partidaDb = null;
					partidaDb = this.daoPartida.save(new EntidadPartida(0, partida.getNombre(), jugador1, jugador2,
							jugadorGuardaPartida, partida.isFinalizada()));
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

	public void actualizarPartida(Partida partida, int jugadorGuardaPartidaId) {
		try {
			// Datos de la BDD //

			EntidadPartida partidaDb = this.daoPartida.findByNombre(partida.getNombre());
			EntidadJugador jugador1Db = partidaDb.getJugador1();
			EntidadJugador jugador2Db = partidaDb.getJugador2();
			EntidadJugador jugadorGuardaPartidaDb = partidaDb.getJugadorGuardaPartida();
			EntidadBase baseJ1Db = this.servicioBase.buscarPorIdJugador(jugador1Db.getId());
			EntidadBase baseJ2Db = this.servicioBase.buscarPorIdJugador(jugador2Db.getId());
			List<EntidadElementoBase> elementosBaseJ1Db = this.servicioElementoBase
					.buscarTodosPorIdBase(baseJ1Db.getId());
			List<EntidadElementoBase> elementosBaseJ2Db = this.servicioElementoBase
					.buscarTodosPorIdBase(baseJ2Db.getId());
			List<EntidadAvion> avionesJ1Db = this.servicioAvion.buscarTodosPorIdJugador(jugador1Db.getId());
			List<EntidadAvion> avionesJ2Db = this.servicioAvion.buscarTodosPorIdJugador(jugador2Db.getId());
			List<EntidadArtilleria> artilleriasJ1Db = this.servicioArtilleria
					.buscarTodosPorIdJugador(jugador1Db.getId());
			List<EntidadArtilleria> artilleriasJ2Db = this.servicioArtilleria
					.buscarTodosPorIdJugador(jugador2Db.getId());

			// Fin datos de la BDD //

			// Actualizar datos de jugadores y partida //

			Jugador jugador1 = partida.getJugadorUno();
			Jugador jugador2 = partida.getJugadorDos();
			/*jugador1Db = (this.servicioJugador.existeJugadorPorNumeroYNombre(jugador1.getId(), jugador1.getNombre()))
					? jugador1Db
					: this.servicioJugador.guardar(jugador1);
			jugador2Db = (this.servicioJugador.existeJugadorPorNumeroYNombre(jugador2.getId(), jugador2.getNombre()))
					? jugador2Db
					: this.servicioJugador.guardar(jugador2);*/
			jugadorGuardaPartidaDb = (jugador1Db.getNumero() == jugadorGuardaPartidaId) ? jugador1Db : jugador2Db;
			partidaDb.setJugador1(jugador1Db);
			partidaDb.setJugador2(jugador2Db);
			partidaDb.setJugadorGuardaPartida(jugadorGuardaPartidaDb);
			this.daoPartida.save(partidaDb);

			// Fin actualizar datos de jugadores y partida //

			// Actualizar datos de las bases //

			baseJ1Db.setJugador(jugador1Db);
			baseJ1Db.setEjeX(jugador1.getBase().getPosicion().getEjeX());
			baseJ1Db.setEjeY(jugador1.getBase().getPosicion().getEjeY());
			baseJ1Db.setAngulo(jugador1.getBase().getPosicion().getAngulo());
			baseJ1Db.setVisible(jugador1.getBase().isVisible());
			this.servicioBase.actualizar(baseJ1Db);
			baseJ2Db.setJugador(jugador2Db);
			baseJ2Db.setEjeX(jugador2.getBase().getPosicion().getEjeX());
			baseJ2Db.setEjeY(jugador2.getBase().getPosicion().getEjeY());
			baseJ2Db.setAngulo(jugador2.getBase().getPosicion().getAngulo());
			baseJ2Db.setVisible(jugador2.getBase().isVisible());
			this.servicioBase.actualizar(baseJ2Db);

			// Fin actualizar datos de las bases //

			// Actualizar datos de los elementos de la base //

			for (EntidadElementoBase elementoBaseJ1Db : elementosBaseJ1Db) {
				for (ElementoBase elementoBaseJ1 : jugador1.getBase().getElementosBase()) {
					if (elementoBaseJ1.getNombre() == elementoBaseJ1Db.getNombre()) {
						elementoBaseJ1Db.setBase(baseJ1Db);
						elementoBaseJ1Db.setEjeX(elementoBaseJ1.getPosicion().getEjeX());
						elementoBaseJ1Db.setEjeY(elementoBaseJ1.getPosicion().getEjeY());
						elementoBaseJ1Db.setAngulo(elementoBaseJ1.getPosicion().getAngulo());
						elementoBaseJ1Db.setDestruido(elementoBaseJ1.isDestruido());
					}
				}
			}

			for (EntidadElementoBase elementoBaseJ2Db : elementosBaseJ2Db) {
				for (ElementoBase elementoBaseJ2 : jugador2.getBase().getElementosBase()) {
					if (elementoBaseJ2.getNombre() == elementoBaseJ2Db.getNombre()) {
						elementoBaseJ2Db.setBase(baseJ2Db);
						elementoBaseJ2Db.setEjeX(elementoBaseJ2.getPosicion().getEjeX());
						elementoBaseJ2Db.setEjeY(elementoBaseJ2.getPosicion().getEjeY());
						elementoBaseJ2Db.setAngulo(elementoBaseJ2.getPosicion().getAngulo());
						elementoBaseJ2Db.setDestruido(elementoBaseJ2.isDestruido());
					}
				}
			}

			// Fin actualizar datos de los elementos de la base //

			// Actualizar datos de los aviones //

			for (EntidadAvion avionJ1Db : avionesJ1Db) {
				for (Avion avionJ1 : jugador1.getListAviones()) {
					if (avionJ1Db.getNumero() == avionJ1.getId()) {
						avionJ1Db.setJugador(jugador1Db);
						avionJ1Db.setEjeX(avionJ1.getPosicion().getEjeX());
						avionJ1Db.setEjeY(avionJ1.getPosicion().getEjeY());
						avionJ1Db.setAngulo(avionJ1.getPosicion().getAngulo());
						avionJ1Db.setEstado(avionJ1.getEstado());
						avionJ1Db.setVida(avionJ1.getVida());
						avionJ1Db.setCombustible(avionJ1.getCombustible());
						avionJ1Db.setTieneBomba(avionJ1.isTieneBomba());
						avionJ1Db.setVisible(avionJ1.isVisible());
						this.servicioAvion.actualizar(avionJ1Db);
					}
				}
			}

			for (EntidadAvion avionJ2Db : avionesJ2Db) {
				for (Avion avionJ2 : jugador2.getListAviones()) {
					if (avionJ2Db.getNumero() == avionJ2.getId()) {
						avionJ2Db.setJugador(jugador2Db);
						avionJ2Db.setEjeX(avionJ2.getPosicion().getEjeX());
						avionJ2Db.setEjeY(avionJ2.getPosicion().getEjeY());
						avionJ2Db.setAngulo(avionJ2.getPosicion().getAngulo());
						avionJ2Db.setEstado(avionJ2.getEstado());
						avionJ2Db.setVida(avionJ2.getVida());
						avionJ2Db.setCombustible(avionJ2.getCombustible());
						avionJ2Db.setTieneBomba(avionJ2.isTieneBomba());
						avionJ2Db.setVisible(avionJ2.isVisible());
						this.servicioAvion.actualizar(avionJ2Db);
					}
				}
			}

			// Fin actualizar datos de los aviones //

			// Actualizar datos de las artillerias //

			for (EntidadArtilleria artilleriaJ1Db : artilleriasJ1Db) {
				for (Artilleria artilleriaJ1 : jugador1.getListArtilleria()) {
					if (artilleriaJ1.getIdArtilleria() == artilleriaJ1Db.getNumero()) {
						artilleriaJ1Db.setJugador(jugador1Db);
						artilleriaJ1Db.setEjeX(artilleriaJ1.getPosicion().getEjeX());
						artilleriaJ1Db.setEjeY(artilleriaJ1.getPosicion().getEjeY());
						artilleriaJ1Db.setAngulo(artilleriaJ1.getPosicion().getAngulo());
						artilleriaJ1Db.setDestruida(artilleriaJ1.isDestruida());
						artilleriaJ1Db.setVisible(artilleriaJ1.isVisible());
						this.servicioArtilleria.actualizar(artilleriaJ1Db);
					}
				}
			}

			for (EntidadArtilleria artilleriaJ2Db : artilleriasJ2Db) {
				for (Artilleria artilleriaJ2 : jugador2.getListArtilleria()) {
					if (artilleriaJ2.getIdArtilleria() == artilleriaJ2Db.getNumero()) {
						artilleriaJ2Db.setJugador(jugador2Db);
						artilleriaJ2Db.setEjeX(artilleriaJ2.getPosicion().getEjeX());
						artilleriaJ2Db.setEjeY(artilleriaJ2.getPosicion().getEjeY());
						artilleriaJ2Db.setAngulo(artilleriaJ2.getPosicion().getAngulo());
						artilleriaJ2Db.setDestruida(artilleriaJ2.isDestruida());
						artilleriaJ2Db.setVisible(artilleriaJ2.isVisible());
						this.servicioArtilleria.actualizar(artilleriaJ2Db);
					}
				}
			}

			// Fin actualizar datos de las artillerias //

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Partida cargarPartida(String nombre, String jugadorNombre) {
		Partida partida = null;
		try {
			EntidadPartida partidaDb = this.daoPartida.findByNombre(partida.getNombre());
			if (jugadorNombre == partidaDb.getJugadorGuardaPartida().getNombre()) {
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
				List<EntidadArtilleria> artilleriasJ1Db = this.servicioArtilleria
						.buscarTodosPorIdJugador(jugador1Db.getId());
				List<EntidadArtilleria> artilleriasJ2Db = this.servicioArtilleria
						.buscarTodosPorIdJugador(jugador2Db.getId());

				// Inicializo datos //

				Jugador jugador1 = new Jugador();
				Jugador jugador2 = new Jugador();
				Base baseJ1 = new Base();
				Base baseJ2 = new Base();
				List<Avion> avionesJ1 = new ArrayList<Avion>();
				List<Avion> avionesJ2 = new ArrayList<Avion>();
				List<Artilleria> artilleriasJ1 = new ArrayList<Artilleria>();
				List<Artilleria> artilleriasJ2 = new ArrayList<Artilleria>();
				List<ElementoBase> elementosBaseJ1 = new ArrayList<ElementoBase>();
				List<ElementoBase> elementosBaseJ2 = new ArrayList<ElementoBase>();

				// Fin inicializo datos //

				// Cargo valores básicos de la partida //

				partida.setNombre(partidaDb.getNombre());
				partida.setFinalizada(partidaDb.isFinalizada());

				// Fin cargo valores básicos de la partida //

				// Cargo valores básicos de cada jugador //

				jugador1.setId(jugador1Db.getNumero());
				jugador1.setNombre(jugador1Db.getNombre());

				jugador2.setId(jugador2Db.getNumero());
				jugador2.setNombre(jugador2Db.getNombre());

				// Fin cargo valores básicos de cada jugador //

				// Cargo las bases y sus elementos en los jugadores //

				for (EntidadElementoBase elementoBaseJ1Db : elementosBaseJ1Db) {
					Posicion posicionJ1 = new Posicion(elementoBaseJ1Db.getEjeX(), elementoBaseJ1Db.getEjeY(),
							elementoBaseJ1Db.getAngulo());
					ElementoBase elementoBaseJ1 = new ElementoBase(elementoBaseJ1Db.getNombre(), posicionJ1,
							elementoBaseJ1Db.isDestruido());
					if (elementoBaseJ1Db.getNombre() == "torreta") {
						elementoBaseJ1.inicializarBalas();
					}
					elementosBaseJ1.add(elementoBaseJ1);
				}

				Posicion posicionBaseJ1 = new Posicion(baseJ1Db.getEjeX(), baseJ1Db.getEjeY(), baseJ1Db.getAngulo());
				baseJ1.setPosicion(posicionBaseJ1);
				baseJ1.setVisible(baseJ1Db.isVisible());
				baseJ1.setElementosBase(elementosBaseJ1);
				jugador1.setBase(baseJ1);

				for (EntidadElementoBase elementoBaseJ2Db : elementosBaseJ2Db) {
					Posicion posicionJ2 = new Posicion(elementoBaseJ2Db.getEjeX(), elementoBaseJ2Db.getEjeY(),
							elementoBaseJ2Db.getAngulo());
					ElementoBase elementoBaseJ2 = new ElementoBase(elementoBaseJ2Db.getNombre(), posicionJ2,
							elementoBaseJ2Db.isDestruido());
					if (elementoBaseJ2Db.getNombre() == "torreta") {
						elementoBaseJ2.inicializarBalas();
					}
					elementosBaseJ2.add(elementoBaseJ2);
				}

				Posicion posicionBaseJ2 = new Posicion(baseJ2Db.getEjeX(), baseJ2Db.getEjeY(), baseJ2Db.getAngulo());
				baseJ2.setPosicion(posicionBaseJ2);
				baseJ2.setVisible(baseJ2Db.isVisible());
				baseJ2.setElementosBase(elementosBaseJ2);
				jugador2.setBase(baseJ2);

				// Fin cargo las bases y sus elementos en los jugadores //

				// Cargo los datos de los aviones al jugador //

				for (EntidadAvion avionJ1Db : avionesJ1Db) {
					Posicion posicionJ1 = new Posicion(avionJ1Db.getEjeX(), avionJ1Db.getEjeY(), avionJ1Db.getAngulo());
					Avion avionJ1 = new Avion();
					avionJ1.setId(avionJ1Db.getNumero());
					avionJ1.setPosicion(posicionJ1);
					avionJ1.setEstado(avionJ1Db.getEstado());
					avionJ1.setVida(avionJ1Db.getVida());
					avionJ1.setCombustible(avionJ1Db.getCombustible());
					avionJ1.setTieneBomba(avionJ1Db.isTieneBomba());
					avionJ1.setVisible(avionJ1Db.isVisible());
					avionJ1.inicializarBalas();
					avionesJ1.add(avionJ1);
				}

				jugador1.setListAviones(avionesJ1);

				for (EntidadAvion avionJ2Db : avionesJ2Db) {
					Posicion posicionJ2 = new Posicion(avionJ2Db.getEjeX(), avionJ2Db.getEjeY(), avionJ2Db.getAngulo());
					Avion avionJ2 = new Avion();
					avionJ2.setId(avionJ2Db.getNumero());
					avionJ2.setPosicion(posicionJ2);
					avionJ2.setEstado(avionJ2Db.getEstado());
					avionJ2.setVida(avionJ2Db.getVida());
					avionJ2.setCombustible(avionJ2Db.getCombustible());
					avionJ2.setTieneBomba(avionJ2Db.isTieneBomba());
					avionJ2.setVisible(avionJ2Db.isVisible());
					avionJ2.inicializarBalas();
					avionesJ2.add(avionJ2);
				}

				jugador2.setListAviones(avionesJ2);

				// Fin cargo los datos de los aviones al jugador //

				// Cargo los datos de las artillerias al jugador //

				for (EntidadArtilleria artilleriaJ1Db : artilleriasJ1Db) {
					Posicion posicionJ1 = new Posicion(artilleriaJ1Db.getEjeX(), artilleriaJ1Db.getEjeY(),
							artilleriaJ1Db.getAngulo());
					Artilleria artilleriaJ1 = new Artilleria();
					artilleriaJ1.setIdArtilleria(artilleriaJ1Db.getNumero());
					artilleriaJ1.setPosicion(posicionJ1);
					artilleriaJ1.setDestruida(artilleriaJ1Db.isDestruida());
					artilleriaJ1.setVisible(artilleriaJ1Db.isVisible());
					artilleriaJ1.inicializarBalas();
					artilleriasJ1.add(artilleriaJ1);
				}

				jugador1.setListArtilleria(artilleriasJ1);

				for (EntidadArtilleria artilleriaJ2Db : artilleriasJ2Db) {
					Posicion posicionJ2 = new Posicion(artilleriaJ2Db.getEjeX(), artilleriaJ2Db.getEjeY(),
							artilleriaJ2Db.getAngulo());
					Artilleria artilleriaJ2 = new Artilleria();
					artilleriaJ2.setIdArtilleria(artilleriaJ2Db.getNumero());
					artilleriaJ2.setPosicion(posicionJ2);
					artilleriaJ2.setDestruida(artilleriaJ2Db.isDestruida());
					artilleriaJ2.setVisible(artilleriaJ2Db.isVisible());
					artilleriaJ2.inicializarBalas();
					artilleriasJ2.add(artilleriaJ2);
				}

				jugador2.setListArtilleria(artilleriasJ2);

				// Fin cargo los datos de las artillerias al jugador //

				partida.setJugadorUno(jugador1);
				partida.setJugadorDos(jugador2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return partida;
	}

}

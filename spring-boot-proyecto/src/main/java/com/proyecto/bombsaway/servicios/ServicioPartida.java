package com.proyecto.bombsaway.servicios;

import com.proyecto.bombsaway.clases.*;
import com.proyecto.bombsaway.controladores.ControladorMensajes;
import com.proyecto.bombsaway.daos.IDAOPartida;
import com.proyecto.bombsaway.dtos.*;
import com.proyecto.bombsaway.excepciones.ConcurrenciaException;
import com.proyecto.bombsaway.manejadores.ManejadorPartida;
import com.proyecto.bombsaway.enumerados.EstadoAvion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioPartida {

	private final int MAX_VIDA = 100;
	private final int DANIO_DISPARO_BALA = 10;
	private final int RADIO_AVION_ALTURA_ALTA = 20;
	private final int RADIO_AVION_ALTURA_BAJA = 14;
	private final int RADIO_BALA = 5;
	private final double RADIO_BOMBA = 3.5;
	private final int RADIO_HANGAR = 15;
	private final double RADIO_TORRETA = 17.5;
	private final int RADIO_TANQUE_COMBUSTIBLE = 15;
	private final int CANTIDAD_ARTILLERIA = 11;
	private final int RADIO_ARTILLERIA = 12;
	private final int RADIO_VISION_AVION = 87;
	private final int RADIO_VISION_BASE = 40;
	private final int RADIO_VISION_ARTILLERIA = 40;
	private final ManejadorPartida manejadorPartida;
	private final ControladorMensajes mensajeriaUpdate;
	private final IDAOPartida DAOPartida;

	@Autowired
	public ServicioPartida(ManejadorPartida manejadorPartida, IDAOPartida DAOPartida,
			ControladorMensajes mensajeriaUpdate) {
		this.DAOPartida = DAOPartida;
		this.manejadorPartida = ManejadorPartida.getManejadorPartida();
		this.mensajeriaUpdate = mensajeriaUpdate;
	}

	public int crearPartidaEnEspera(DTOPartidaEnEspera partidaEnEspera) {
		try {
			PartidaEnEspera nuevaPartida = new PartidaEnEspera(partidaEnEspera.getNombrePartida(),
					partidaEnEspera.getNombreJugador());
			this.manejadorPartida.addPartidaEnEspera(nuevaPartida);
			return 1;
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception error) {
			error.printStackTrace();
		}
		return 1;
	}

	private Partida crearPartida(String nombrePartida, String nombreJugadorUno, String nombreJugadorDos) {
		Jugador jugadorUno = new Jugador(nombreJugadorUno);
		jugadorUno.setId(1);
		Jugador jugadorDos = new Jugador(nombreJugadorDos);
		jugadorDos.setId(2);
		List<Avion> listAvionesUno = new ArrayList<Avion>();
		List<Avion> listAvionesDos = new ArrayList<Avion>();

		// inicializacion aviones de cada jugador
		for (int i = 0; i <= 3; i++) {
			Avion avionJugadorUno = new Avion();
			avionJugadorUno.setId(i);
			avionJugadorUno.setVida(this.MAX_VIDA);
			avionJugadorUno.setEstado(EstadoAvion.QUIETO);
			avionJugadorUno.setVisible(false);
			avionJugadorUno.setPosicion(new Posicion(540, 50, 90));

			avionJugadorUno.inicializarBalas();
			listAvionesUno.add(avionJugadorUno);

			Avion avionJugadorDos = new Avion();
			avionJugadorDos.setId(i);
			avionJugadorDos.setVida(this.MAX_VIDA);
			avionJugadorDos.setEstado(EstadoAvion.QUIETO);
			avionJugadorDos.setVisible(false);
			avionJugadorDos.setPosicion(new Posicion(540, 670, -90));
			avionJugadorDos.inicializarBalas();
			listAvionesDos.add(avionJugadorDos);
		}
		jugadorUno.setListAviones(listAvionesUno);
		jugadorDos.setListAviones(listAvionesDos);

		// creacion nueva partida
		return new Partida(jugadorUno, jugadorDos, nombrePartida);
	}

	public DTOMensaje unirseAPartida(DTOUsuario usuario) {
		try {
			List<PartidaEnEspera> partidasEnEpera = this.manejadorPartida.getPartidasEnEspera();
			if (partidasEnEpera != null && !partidasEnEpera.isEmpty()) {
				PartidaEnEspera partidaEnEspera = partidasEnEpera.get(0);
				Partida partidaEnJuego = this.crearPartida(partidaEnEspera.getNombrePartida(),
						partidaEnEspera.getNombreJugador(), usuario.getNombreJugador());
				this.manejadorPartida.addPartidaEnJuego(partidaEnJuego);
				return new DTOMensaje("Bootloader");
			} else {
				return new DTOMensaje("No se encuentran partidas creadas");
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception error) {
			error.printStackTrace();
		}
		return new DTOMensaje("sucedio un error en unirse a partida error");
	}

	public void colocarBase(DTOBase baseDto) {
		try {
			Partida partida = this.recuperarPartida(baseDto.getNombrePartida());
			if (partida != null) {
				Jugador jugadorActual = (baseDto.getIdJugador() == 1) ? partida.getJugadorUno()
						: partida.getJugadorDos();
				Posicion posicion = new Posicion(baseDto.getBaseEjeX(), baseDto.getBaseEjeY(), baseDto.getBaseEjeY());
				ElementoBase hangar = new ElementoBase("hangar",
						new Posicion(baseDto.getHangarEjeX(), baseDto.getHangarEjeY(), 0), false);
				ElementoBase torreta = new ElementoBase("torreta",
						new Posicion(baseDto.getTorretaEjeX(), baseDto.getTorretaEjeY(), 0), false);
				ElementoBase tanqueCombustible = new ElementoBase("tanque_combustible",
						new Posicion(baseDto.getTanqueCombustibleEjeX(), baseDto.getTanqueCombustibleEjeY(), 0), false);
				List<ElementoBase> elementosBase = new ArrayList<ElementoBase>();
				elementosBase.add(hangar);
				elementosBase.add(torreta);
				elementosBase.add(tanqueCombustible);
				Base baseActual = new Base(posicion, elementosBase);
				baseActual.setVisible(false);
				jugadorActual.setBase(baseActual);
				if (jugadorActual.getId() == 1) {
					partida.setJugadorUno(jugadorActual);
				} else {
					partida.setJugadorDos(jugadorActual);
				}
				this.manejadorPartida.updatePartidaEnJuego(partida);
			}
			if (partida.getJugadorUno().getBase() != null && partida.getJugadorDos().getBase() != null) {
				DTOBase baseJugadorUno = partida.getJugadorUno().getBase().getDTO();
				baseJugadorUno.setIdJugador(partida.getJugadorUno().getId());
				baseJugadorUno.setNombrePartida(partida.getNombre());
				DTOBase baseJugadorDos = partida.getJugadorDos().getBase().getDTO();
				baseJugadorDos.setIdJugador(partida.getJugadorDos().getId());
				baseJugadorDos.setNombrePartida(partida.getNombre());
				String notificacion = "[" + baseJugadorUno.toString() + ", " + baseJugadorDos.toString() + "]";
				this.mensajeriaUpdate.sendColocarBases(notificacion);
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tirarBomba(DTOBomba bombaDto) {
		try {

			Partida partida = this.recuperarPartida(bombaDto.getNombrePartida());
			if (partida != null) {
				Jugador jugadorActual = (bombaDto.getIdJugador() == 1) ? partida.getJugadorUno()
						: partida.getJugadorDos();
				List<Avion> aviones = jugadorActual.getListAviones();
				Avion avion = aviones.get(bombaDto.getAvionId());
				avion.setTieneBomba(false);
				aviones.set(avion.getId(), avion);
				jugadorActual.setListAviones(aviones);
				if (bombaDto.getIdJugador() == 1) {
					partida.setJugadorUno(jugadorActual);
				} else {
					partida.setJugadorDos(jugadorActual);
				}
				Jugador jugadorEnemigo = (bombaDto.getIdJugador() == 1) ? partida.getJugadorDos()
						: partida.getJugadorUno();
				Base base = jugadorEnemigo.getBase();
				List<ElementoBase> elementosBase = new ArrayList<ElementoBase>();
				boolean destruidoHangar = false;
				boolean destruidoTorreta = false;
				boolean destruidoTanqueCombustible = false;
				for (ElementoBase elemento : jugadorEnemigo.getBase().getElementosBase()) {
					if (elemento.getNombre() == "torreta") {
						destruidoTorreta = elemento.isDestruido();
					} else if (elemento.getNombre() == "hangar") {
						destruidoHangar = elemento.isDestruido();
					} else if (elemento.getNombre() == "tanque_combustible") {
						destruidoTanqueCombustible = elemento.isDestruido();
					}
				}
				DTOEstadoBase estadoBase = new DTOEstadoBase(partida.getNombre(), jugadorEnemigo.getId(),
						destruidoHangar, destruidoTanqueCombustible, destruidoTorreta);
				for (ElementoBase elementoBase : base.getElementosBase()) {
					if (elementoBase.getNombre() == "torreta") {
						if (this.validarImpactoBombaRadio(bombaDto, elementoBase.getPosicion(), this.RADIO_TORRETA)) {
							estadoBase.setTorretaDestruida(true);
							elementoBase.setDestruido(true);
						}
						elementosBase.add(elementoBase);
					} else if (elementoBase.getNombre() == "hangar") {
						if (this.validarImpactoBombaRadio(bombaDto, elementoBase.getPosicion(), this.RADIO_HANGAR)) {
							estadoBase.setHangarDestruido(true);
							elementoBase.setDestruido(true);
							List<Avion> avionesEnemigos = jugadorEnemigo.getListAviones();
							for (Avion avionEnemigo : jugadorEnemigo.getListAviones()) {
								if (avionEnemigo.getEstado() == EstadoAvion.QUIETO) {
									avionEnemigo.setEstado(EstadoAvion.DESTRUIDO);
									avionesEnemigos.set(avionEnemigo.getId(), avionEnemigo);
									DTOAvion avionDto = avionEnemigo.getDTO();
									avionDto.setNombrePartida(partida.getNombre());
									avionDto.setIdJugador(jugadorEnemigo.getId());
									this.estallarAvion(avionDto.toString());
								}
							}
							jugadorEnemigo.setListAviones(avionesEnemigos);
						}
						elementosBase.add(elementoBase);
					} else if (elementoBase.getNombre() == "tanque_combustible") {
						if (this.validarImpactoBombaRadio(bombaDto, elementoBase.getPosicion(),
								this.RADIO_TANQUE_COMBUSTIBLE)) {
							estadoBase.setTanqueCombustibleDestruido(true);
							elementoBase.setDestruido(true);
						}
						elementosBase.add(elementoBase);
					}
				}
				base.setElementosBase(elementosBase);
				jugadorEnemigo.setBase(base);
				if (bombaDto.getIdJugador() == 1) {
					partida.setJugadorDos(jugadorEnemigo);
				} else {
					partida.setJugadorUno(jugadorEnemigo);
				}
				this.manejadorPartida.updatePartidaEnJuego(partida);
				this.mensajeriaUpdate.sendEstadoElementosBase(estadoBase.toString());
				this.comprobarImpactoArtilleria(partida, jugadorEnemigo, bombaDto);
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void colocarArtilleria(DTOArtilleria artilleriaDto) {
		try {
			Partida partida = this.recuperarPartida(artilleriaDto.getNombrePartida());
			if (partida != null) {
				Jugador jugadorActual = (artilleriaDto.getIdJugador() == 1) ? partida.getJugadorUno()
						: partida.getJugadorDos();
				if (jugadorActual.getListArtilleria().size() < this.CANTIDAD_ARTILLERIA) {
					Artilleria artilleria = new Artilleria(artilleriaDto.getIdArtilleria(),
							new Posicion(artilleriaDto.getEjeX(), artilleriaDto.getEjeY(), artilleriaDto.getAngulo()),
							false);
					artilleria.setVisible(false);
					List<Artilleria> listArtilleria = jugadorActual.getListArtilleria();
					listArtilleria.add(artilleria);
					jugadorActual.setListArtilleria(listArtilleria);
					if (artilleriaDto.getIdJugador() == 1) {
						partida.setJugadorUno(jugadorActual);
					} else {
						partida.setJugadorDos(jugadorActual);
					}
					this.manejadorPartida.updatePartidaEnJuego(partida);
				}
			}
			if (partida.getJugadorUno().getListArtilleria().size() == this.CANTIDAD_ARTILLERIA
					&& partida.getJugadorDos().getListArtilleria().size() == this.CANTIDAD_ARTILLERIA) {
				this.actualizarArtilleria(partida);
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void actualizarArtilleria(Partida partida) {
		String artilleriaJugadorUno = "[";
		int i = 0;
		for (Artilleria artilleria1 : partida.getJugadorUno().getListArtilleria()) {
			Posicion posicion = artilleria1.getPosicion();
			DTOArtilleria artilleriaDtoJ1 = new DTOArtilleria(partida.getNombre(), partida.getJugadorUno().getId(), i,
					posicion.getEjeX(), posicion.getEjeY(), posicion.getAngulo(), artilleria1.isDestruida());

			artilleriaJugadorUno = (i == 0) ? artilleriaJugadorUno + artilleriaDtoJ1.toString()
					: artilleriaJugadorUno + ", " + artilleriaDtoJ1.toString();
			i++;
		}
		artilleriaJugadorUno += "]";
		i = 0;
		String artilleriaJugadorDos = "[";
		for (Artilleria artilleria2 : partida.getJugadorDos().getListArtilleria()) {
			Posicion posicion = artilleria2.getPosicion();
			DTOArtilleria artilleriaDtoJ2 = new DTOArtilleria(partida.getNombre(), partida.getJugadorDos().getId(), i,
					posicion.getEjeX(), posicion.getEjeY(), posicion.getAngulo(), artilleria2.isDestruida());

			artilleriaJugadorDos = (i == 0) ? artilleriaJugadorDos + artilleriaDtoJ2.toString()
					: artilleriaJugadorDos + ", " + artilleriaDtoJ2.toString();
			i++;
		}
		artilleriaJugadorDos += "]";
		String notificacion = "[" + artilleriaJugadorUno + ", " + artilleriaJugadorDos + "]";
		this.mensajeriaUpdate.sendActualizarArtilleria(notificacion);
	}

	private boolean validarImpactoBombaRadio(DTOBomba bombaDto, Posicion posicion, double radioElemento) {
		boolean res = false;
		double distancia = 0;

		int coordX1 = bombaDto.getEjeX();
		int coordY1 = bombaDto.getEjeY();
		int coordX2 = posicion.getEjeX();
		int coordY2 = posicion.getEjeY();

		double ecuacion = ((coordX1 - coordX2) * (coordX1 - coordX2) + (coordY1 - coordY2) * (coordY1 - coordY2));
		distancia = Math.sqrt(ecuacion);

		if (distancia < this.RADIO_BOMBA + radioElemento) {
			res = true;
		}
		return res;
	}

	public void comprobarImpactoArtilleria(Partida partida, Jugador jugador, DTOBomba bombaDto) {
		try {
			List<Artilleria> artillerias = jugador.getListArtilleria();
			boolean huboCambio = false;
			for (Artilleria artilleria : artillerias) {
				if (this.validarImpactoBombaRadio(bombaDto, artilleria.getPosicion(), this.RADIO_ARTILLERIA)) {
					huboCambio = true;
					artilleria.setDestruida(true);
					DTOArtilleria artilleriaDto = new DTOArtilleria(partida.getNombre(), jugador.getId(),
							artilleria.getIdArtilleria(), artilleria.getPosicion().getEjeX(),
							artilleria.getPosicion().getEjeY(), artilleria.getPosicion().getAngulo(), true);
					this.mensajeriaUpdate.sendDestruirArtilleria(artilleriaDto.toString());
				}
			}
			if (huboCambio) {
				jugador.setListArtilleria(artillerias);
				if (jugador.getId() == 1) {
					partida.setJugadorUno(jugador);
				} else {
					partida.setJugadorDos(jugador);
				}
				this.manejadorPartida.updatePartidaEnJuego(partida);
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkCombustibleAvion(DTOAvion avionDTO) {
		return avionDTO.getCombustible() < 1;
	}

	public void moverAvion(DTOAvion avionDTO) {
		try {
			String notificacion = null;
			// si el avion se fue de los limites, estalla
			boolean avionFueraLimites = this.checkAvionFueraLimites(avionDTO);
			boolean avionSinCombustible = this.checkCombustibleAvion(avionDTO);
			DTOAvion avionChoqueDto = this.checkChoqueEntreAviones(avionDTO);
			Partida partida = recuperarPartida(avionDTO.getNombrePartida());
			//Se actualiza la visibilidad de los elementos enemigos
			this.updateVisibilidad2(avionDTO);
			if (avionFueraLimites || avionSinCombustible) {
				avionDTO.setEstado(EstadoAvion.DESTRUIDO);
				// se actualiza la partida y se envia el avion a estallar
				notificacion = this.updateAvionEnPartida(avionDTO, partida);
				this.estallarAvion(notificacion.toString());
			} else if (avionChoqueDto != null) {
				avionDTO.setEstado(EstadoAvion.DESTRUIDO);
				avionChoqueDto.setEstado(EstadoAvion.DESTRUIDO);
				this.estallarAvion(avionDTO.toString());
				this.estallarAvion(avionChoqueDto.toString());
				this.updateAvionEnPartida(avionDTO, partida);
				this.updateAvionEnPartida(avionChoqueDto, partida);
			} else {
				// se actualiza la partida y se envia el status del avion a el canal
				notificacion = this.updateAvionEnPartida(avionDTO, partida);
				this.mensajeriaUpdate.sendAvionesEnemigos(notificacion.toString());
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	private void estallarAvion(String data) {
		this.mensajeriaUpdate.sendEstallarAviones(data);
	}

	private boolean validarImpactoRadioAvion(Posicion posicion, Avion avion) {
		boolean res = false;
		double distancia = 0;
		int radioAvion;
		if (avion.getEstado() == EstadoAvion.ALTURA_ALTA) {
			radioAvion = this.RADIO_AVION_ALTURA_ALTA;
		} else {
			radioAvion = this.RADIO_AVION_ALTURA_BAJA;
		}
		int coordX1 = posicion.getEjeX();
		int coordY1 = posicion.getEjeY();
		int coordX2 = avion.getPosicion().getEjeX();
		int coordY2 = avion.getPosicion().getEjeY();

		double ecuacion = ((coordX1 - coordX2) * (coordX1 - coordX2) + (coordY1 - coordY2) * (coordY1 - coordY2));
		distancia = Math.sqrt(ecuacion);
		if (distancia < this.RADIO_BALA + radioAvion) {
			res = true;
		}
		return res;
	}

	private DTOAvion impactarAvion(Avion avion) {
		DTOAvion dtoAvion = null;

		if (avion.getEstado() != EstadoAvion.DESTRUIDO) {
			if (avion.getVida() > this.DANIO_DISPARO_BALA) {
				// el avion tiene suficiente vida para recibir disparo, la vida desciende
				int vidaActual = avion.getVida();
				avion.setVida(vidaActual - this.DANIO_DISPARO_BALA);
				dtoAvion = avion.getDTO();
			} else {
				// el avion tiene poca vida y el disparo lo hace estallar
				avion.setEstado(EstadoAvion.DESTRUIDO);
				avion.setVida(0);
				dtoAvion = avion.getDTO();
			}
		}
		return dtoAvion;
	}

	public DTOAvion impactoBalaEnAvion(DTOBala balaDto, Jugador jugadorEnemigo) {
		boolean impacto = false;
		DTOAvion dtoAvion = null;
		List<Avion> listAvionesEnemigos = jugadorEnemigo.getListAviones();
		int i = 0;
		while (!impacto && i < listAvionesEnemigos.size()) {
			Avion avion = listAvionesEnemigos.get(i);
			if (avion.getEstado() == balaDto.getAltitud()) {
				Posicion posicionBala = new Posicion(balaDto.getEjeX(), balaDto.getEjeY(), balaDto.getAngulo());
				// chequeamos que la bala pertenezca al radio del avion
				if (avion.getEstado() != EstadoAvion.DESTRUIDO) {
					impacto = this.validarImpactoRadioAvion(posicionBala, avion);
					if (impacto) {
						dtoAvion = impactarAvion(avion);
						dtoAvion.setNombrePartida(balaDto.getNombrePartida());
						dtoAvion.setIdJugador(jugadorEnemigo.getId());
					}
				}
			}
			i++;
		}
		return dtoAvion;
	}

	public void dispararBala(DTOBala balaDto) {
		try {
			Partida partida = this.recuperarPartida(balaDto.getNombrePartida());
			if (partida != null) {
				Jugador jugadorEnemigo = null;
				Jugador jugadorActual = null;
				if (balaDto.getIdJugador() == 1) {
					jugadorEnemigo = partida.getJugadorDos();
					jugadorActual = partida.getJugadorUno();
				} else {
					jugadorEnemigo = partida.getJugadorUno();
					jugadorActual = partida.getJugadorDos();
				}

				List<Avion> listAvionesJugadorActual = jugadorActual.getListAviones();
				Avion avionAutorDisparo = listAvionesJugadorActual.get(balaDto.getIdAvion());
				Bala balaDisparada = avionAutorDisparo.getListBalas().get(balaDto.getIdBala());
				if (balaDisparada.isVisible()) {
					// se obtienen los aviones del enemigo para chequear si impacto o no
					DTOAvion dtoAvion = this.impactoBalaEnAvion(balaDto, jugadorEnemigo);
					if (dtoAvion != null) {
						// si la bala impacto contra avion enemigo
						if (dtoAvion.getEstado() == EstadoAvion.DESTRUIDO) {
							this.estallarAvion(dtoAvion.toString());
						} else {
							this.bajarVidaAvion(dtoAvion.toString());
						}
						balaDisparada.setVisible(false);
						balaDto.setVisible(false);
						this.mensajeriaUpdate.sendPosicionBala(balaDto.toString());
					} else {
						// se actualiza la posicion de la bala y se avisa a los clientes
						balaDisparada
								.setPosicion(new Posicion(balaDto.getEjeX(), balaDto.getEjeY(), balaDto.getAngulo()));
						// chequeamos que si la visibilidad de la bala cambio, si cambio notificamos y
						// actualizamos la bala
						if (!balaDto.isVisible()) {
							balaDisparada.setVisible(balaDto.isVisible());
							this.mensajeriaUpdate.sendPosicionBala(balaDto.toString());
						}
					}
				}
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public void primerDisparoBala(DTOBala balaDto) {
		try {
			Partida partida = this.recuperarPartida(balaDto.getNombrePartida());
			if (partida != null) {
				Jugador jugadorEnemigo = null;
				Jugador jugadorActual = null;
				if (balaDto.getIdJugador() == 1) {
					jugadorEnemigo = partida.getJugadorDos();
					jugadorActual = partida.getJugadorUno();
				} else {
					jugadorEnemigo = partida.getJugadorUno();
					jugadorActual = partida.getJugadorDos();
				}
				List<Avion> listAvionesJugadorActual = jugadorActual.getListAviones();
				Avion avionAutorDisparo = listAvionesJugadorActual.get(balaDto.getIdAvion());
				List<Bala> listaBalas = avionAutorDisparo.getListBalas();
				Bala balaDisparada = avionAutorDisparo.getListBalas().get(balaDto.getIdBala());
				balaDisparada.setVisible(true);
				this.mensajeriaUpdate.sendPosicionBala(balaDto.toString());
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	private String getMensajeError(String mensajeError) {
		String res = "{\"error\":\"" + mensajeError + "}";
		return res;
	}

	private void bajarVidaAvion(String data) {
		this.mensajeriaUpdate.sendBajarVidaAvion(data);
	}

	private Partida recuperarPartida(String nombrePartida) throws ConcurrenciaException {
		return this.manejadorPartida.getPartidaEnJuego(nombrePartida);
	}

	private boolean validarImpactoRadioAviones(Posicion posicionAvionActual, Posicion posicionAvionEnemigo,
			int radioAvion) {
		boolean res = false;
		int coordX1 = posicionAvionActual.getEjeX();
		int coordY1 = posicionAvionActual.getEjeY();
		int coordX2 = posicionAvionEnemigo.getEjeX();
		int coordY2 = posicionAvionEnemigo.getEjeY();
		double ecuacion = ((coordX1 - coordX2) * (coordX1 - coordX2) + (coordY1 - coordY2) * (coordY1 - coordY2));
		double distancia = Math.sqrt(ecuacion);
		if (distancia < 2 * radioAvion) {
			res = true;
		}
		return res;
	}

	private DTOAvion checkChoqueEntreAviones(DTOAvion avionDto) throws ConcurrenciaException {
		DTOAvion avionImpactado = null;
		Partida partida = this.recuperarPartida(avionDto.getNombrePartida());
		if (partida != null) {
			Jugador jugadorEnemigo = avionDto.getIdJugador() == 1 ? partida.getJugadorDos() : partida.getJugadorUno();
			Jugador jugadorActual = avionDto.getIdJugador() == 1 ? partida.getJugadorUno() : partida.getJugadorDos();

			List<Avion> listaAvionesEnemigos = jugadorEnemigo.getListAviones();
			int i = 0;
			boolean impacto = false;
			while (i < listaAvionesEnemigos.size() && !impacto) {
				Avion avionEnemigo = listaAvionesEnemigos.get(i);
				if (avionEnemigo.getEstado() == avionDto.getEstado()) {
					int radioAvion = avionDto.getEstado() == EstadoAvion.ALTURA_BAJA ? this.RADIO_AVION_ALTURA_BAJA
							: this.RADIO_AVION_ALTURA_ALTA;
					impacto = this.validarImpactoRadioAviones(
							new Posicion(avionDto.getEjeX(), avionDto.getEjeY(), avionDto.getAngulo()),
							avionEnemigo.getPosicion(), radioAvion);
					if (impacto) {
						Avion avionActual = jugadorActual.getListAviones().get(avionDto.getIdAvion());
						avionActual.setEstado(EstadoAvion.DESTRUIDO);
						avionEnemigo.setEstado(EstadoAvion.DESTRUIDO);
						// se crea el DTOAvion del avion enemigo dstruido.
						avionImpactado = avionEnemigo.getDTO();
						avionImpactado.setNombrePartida(avionDto.getNombrePartida());
						avionImpactado.setIdJugador(jugadorEnemigo.getId());
						impacto = true;
					}
				}
				i++;
			}
		}
		return avionImpactado;
	}

	private boolean checkAvionFueraLimites(DTOAvion dtoAvion) {
		boolean res = false;
		if (dtoAvion.getEjeX() <= 10 || dtoAvion.getEjeX() >= 1070 || dtoAvion.getEjeY() <= 10
				|| dtoAvion.getEjeY() > 710) {
			res = true;
		}
		return res;
	}

	private String updateAvionEnPartida(DTOAvion avionDTO, Partida partida) throws ConcurrenciaException {
		DTOAvion notificacion = null;
		if (partida != null) {
			Jugador jugador = null;
			Avion avion = null;
			if (avionDTO.getIdJugador() == 1) {
				jugador = partida.getJugadorUno();
				avion = partida.getJugadorUno().getListAviones().get(avionDTO.getIdAvion());

				// se crea la notificacion para dibujar el avion enemigo
				notificacion = new DTOAvion(avionDTO);

				// actualizar avion, en usuario de partida
				avion.updateAvion(avionDTO);
				jugador.getListAviones().set(avion.getId(), avion);
				partida.setJugadorUno(jugador);
			} else {
				jugador = partida.getJugadorDos();
				avion = partida.getJugadorDos().getListAviones().get(avionDTO.getIdAvion());

				// se crea la notificacion para dibujar el avion enemigo
				notificacion = new DTOAvion(avionDTO);

				// actualizar avion, en usuario de partida
				avion.updateAvion(avionDTO);
				jugador.getListAviones().set(avion.getId(), avion);
				partida.setJugadorDos(jugador);
			}
			this.manejadorPartida.updatePartidaEnJuego(partida);
		}
		return notificacion.toString();
	}

	public void moverArtilleria(DTOArtilleria artilleriaDto) {
		try {
			Partida partida = this.recuperarPartida(artilleriaDto.getNombrePartida());
			if (partida != null) {
				Jugador jugadorActual = (artilleriaDto.getIdJugador() == 1) ? partida.getJugadorUno()
						: partida.getJugadorDos();
				Artilleria artilleria = jugadorActual.getListArtilleria().get(artilleriaDto.getIdArtilleria());
				artilleria.setPosicion( new Posicion(artilleriaDto.getEjeX(), artilleriaDto.getEjeY(),
						artilleriaDto.getAngulo()));
				this.mensajeriaUpdate.sendArtilleriaMovida(artilleriaDto.toString());
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private boolean checkVisibilidadBase(DTOAvion avionDto) {
//		return true;
//	}

	private boolean checkVisibilidad(DTOAvion avionDto, String elemento, Posicion posicion) {
		boolean res = false;
		int coordX1 = avionDto.getEjeX();
		int coordY1 = avionDto.getEjeY();
		int coordX2 = posicion.getEjeX();
		int coordY2 = posicion.getEjeY();
		int radioElemento;
		switch (elemento) {
			case "avion": {
				radioElemento = this.RADIO_VISION_AVION;
				break;
			}
			case "base": {
				radioElemento = this.RADIO_VISION_BASE;
				break;
			}
		}
		radioElemento = elemento.equalsIgnoreCase("avion") ? this.RADIO_VISION_AVION:
				this.RADIO_ARTILLERIA;
		double ecuacion = ((coordX1 - coordX2) * (coordX1 - coordX2) + (coordY1 - coordY2) * (coordY1 - coordY2));
		double distancia = Math.sqrt(ecuacion);
		if (distancia < radioElemento + this.RADIO_VISION_AVION) {
			res = true;
		}
		return res;
	}


	public void updateVisibilidad2(DTOAvion avionDto) {
		DTOVisibilidad res = new DTOVisibilidad();
		res.setIdJugador(avionDto.getIdJugador());
		res.setNombrePartida(avionDto.getNombrePartida());

		boolean cambiosEnBase = false;
		boolean cambiosEnAviones = false;
		boolean cambiosEnArtilleriaAvion = false;
		boolean cambiosEnArtilleria = false;

		try {
			Partida partida = this.recuperarPartida(avionDto.getNombrePartida());
			if (partida != null) {
				Jugador jugadorEnemigo = avionDto.getIdJugador() == 1 ? partida.getJugadorDos() : partida.getJugadorUno();
				Jugador jugadorActual = avionDto.getIdJugador() == 1 ? partida.getJugadorUno() : partida.getJugadorDos();
				Avion avionActual = jugadorActual.getListAviones().get(avionDto.getIdAvion());
				boolean visibilidadAvionBase = jugadorEnemigo.getBase().isVisible();
				List<Boolean> visibilidadAvionAviones = new ArrayList<Boolean>();
				List<Boolean> visibilidadArtillerialist = new ArrayList<Boolean>();
				List<Boolean> visibilidadAvionTorretas = new ArrayList<Boolean>();

				//se obtiene la base y se chequea si es visible
				Base baseEnemiga = jugadorEnemigo.getBase();
				boolean esVisibleBase = this.checkVisibilidad(avionDto, "base", baseEnemiga.getPosicion());
				if(esVisibleBase != baseEnemiga.isVisible()) {
					baseEnemiga.setVisible(esVisibleBase);
					cambiosEnBase = true;
					visibilidadAvionBase = esVisibleBase;
				}
				res.setVisibilidadBase(esVisibleBase);

				//se obtienen los los aviones y se qchequean si son visibles
				List<Avion> listAvionesEnemigos = jugadorEnemigo.getListAviones();
				List<Boolean> listVisibilidadAviones = new ArrayList<>();
				for (Avion avion: listAvionesEnemigos) {
					if(avion.getEstado() == EstadoAvion.ALTURA_BAJA ||
							avion.getEstado() == EstadoAvion.ALTURA_ALTA) {
						Boolean visibilidadAvion = this.checkVisibilidad(avionDto, "avion", avion.getPosicion());
						if(visibilidadAvion != avion.isVisible()) {
							avion.setVisible(visibilidadAvion);
							cambiosEnAviones = true;
							visibilidadAvionAviones.add(visibilidadAvion);
						}
					}
					listVisibilidadAviones.add(avion.getId(), avion.isVisible());
				}
				res.setVisibilidadAviones(listVisibilidadAviones);

				//se obtiene la artilleria y se chequean las visibilidades
				List<Artilleria> listArtilleriaEnemigos = jugadorEnemigo.getListArtilleria();
				List<Boolean> listVisibilidadArtilleria = new ArrayList<>();
				for (Artilleria artilleria: listArtilleriaEnemigos) {
					boolean visibilidadArtilleria = false;
					boolean visibilidadAvionArtilleria = false;
					int coordX1 = avionDto.getEjeX();
					int coordY1 = avionDto.getEjeY();
					int coordX2 = artilleria.getPosicion().getEjeX();
					int coordY2 = artilleria.getPosicion().getEjeY();
					double ecuacion = ((coordX1 - coordX2) * (coordX1 - coordX2) + (coordY1 - coordY2) * (coordY1 - coordY2));
					double distancia = Math.sqrt(ecuacion);

					if (distancia < this.RADIO_VISION_ARTILLERIA) {
						visibilidadArtilleria = true;
					}

					if (distancia < this.RADIO_VISION_AVION) {
						visibilidadAvionArtilleria = true;
					}

					if(visibilidadArtilleria != artilleria.isVisible()) {
						cambiosEnArtilleria = true;
						visibilidadArtillerialist.add(visibilidadArtilleria);
					}

					if(visibilidadAvionArtilleria != avionActual.isVisible()) {
						artilleria.setVisible(visibilidadAvionArtilleria);
						cambiosEnArtilleriaAvion = true;
						visibilidadAvionTorretas.add(visibilidadAvionArtilleria);
					}
					listVisibilidadArtilleria.add(artilleria.getIdArtilleria(), visibilidadArtilleria);
				}
				res.setVisibilidadArtilleria(listVisibilidadArtilleria);

				boolean esVisibleAvion = false;

				if(cambiosEnBase) {
					esVisibleAvion = visibilidadAvionBase;
				}

				if(cambiosEnAviones) {
					for (Boolean visible: visibilidadAvionAviones) {
						esVisibleAvion = esVisibleAvion || visible;
					}
				}

				if(cambiosEnArtilleriaAvion) {
					for (Boolean visible: listVisibilidadArtilleria) {
						esVisibleAvion = esVisibleAvion || visible;
					}
				}

				if(cambiosEnArtilleria) {
					for (Boolean visible: visibilidadArtillerialist) {
						esVisibleAvion = esVisibleAvion || visible;
					}
				}

				if(cambiosEnBase || cambiosEnAviones || cambiosEnArtilleriaAvion || cambiosEnArtilleria) {
					avionDto.setVisible(esVisibleAvion);
					this.mensajeriaUpdate.sendAvionEnemigoVisible(avionDto.toString());

					if(cambiosEnBase || cambiosEnAviones || cambiosEnArtilleriaAvion) {
						this.mensajeriaUpdate.sendElementosVisibles(res.toString());
					}
				}

			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateVisibilidad(DTOAvion avionDto) throws ConcurrenciaException {
//		DTOVisibilidad res = new DTOVisibilidad();
//		res.setIdJugador(avionDto.getIdJugador());
//		res.setNombrePartida(avionDto.getNombrePartida());
//		boolean huboCambios = false;
//		Partida partida = this.recuperarPartida(avionDto.getNombrePartida());
//		if (partida != null) {
//			Jugador jugadorEnemigo = avionDto.getIdJugador() == 1 ? partida.getJugadorDos() : partida.getJugadorUno();
//			Jugador jugadorActual = avionDto.getIdJugador() == 1 ? partida.getJugadorUno() : partida.getJugadorDos();
//
//			Avion avionActual = jugadorActual.getListAviones().get(avionDto.getIdAvion());
//			//se obtiene la base y se chequea si es visible
//			Base baseEnemiga = jugadorEnemigo.getBase();
//			boolean esVisibleBase = this.checkVisibilidad(avionDto, "base", baseEnemiga.getPosicion());
//			if(esVisibleBase != baseEnemiga.isVisible()) {
//				huboCambios = true;
//				avionActual.setVisible(esVisibleBase);
//				avionDto.setVisible(esVisibleBase);
//				this.mensajeriaUpdate.sendAvionEnemigoVisible(avionDto.toString());
//				baseEnemiga.setVisible(esVisibleBase);
//			}
//			res.setVisibilidadBase(esVisibleBase);
//
//			//se obtienen los los aviones y se qchequean si son visibles
//			List<Avion> listAvionesEnemigos = jugadorEnemigo.getListAviones();
//			List<Boolean> listVisibilidadAviones = new ArrayList<>();
//			for (Avion avion: listAvionesEnemigos) {
//				Boolean visibilidadAvion = avion.isVisible();
//				if(avion.getEstado() == EstadoAvion.ALTURA_BAJA ||
//						avion.getEstado() == EstadoAvion.ALTURA_ALTA) {
//					if(!huboCambios) {
//						visibilidadAvion = this.checkVisibilidad(avionDto, "avion", avion.getPosicion());
//						if(visibilidadAvion != avion.isVisible()) {
//							avion.setVisible(visibilidadAvion);
//
//							avionActual.setVisible(visibilidadAvion);
//							avionDto.setVisible(visibilidadAvion);
//							this.mensajeriaUpdate.sendAvionEnemigoVisible(avionDto.toString());
//							huboCambios = true;
//
//						}
//					}
//
//				}
//				listVisibilidadAviones.add(avion.getId(), visibilidadAvion);
//			}
//
//			res.setVisibilidadAviones(listVisibilidadAviones);
//
//			//se obtiene la artilleria y se chequean las visibilidades
//			List<Artilleria> listArtilleriaEnemigos = jugadorEnemigo.getListArtilleria();
//			List<Boolean> listVisibilidadArtilleria = new ArrayList<>();
//			for (Artilleria artilleria: listArtilleriaEnemigos) {
//				boolean visibilidadArtilleria = false;
//				boolean visibilidadAvionArtilleria = false;
//				int coordX1 = avionDto.getEjeX();
//				int coordY1 = avionDto.getEjeY();
//				int coordX2 = artilleria.getPosicion().getEjeX();
//				int coordY2 = artilleria.getPosicion().getEjeY();
//				double ecuacion = ((coordX1 - coordX2) * (coordX1 - coordX2) + (coordY1 - coordY2) * (coordY1 - coordY2));
//				double distancia = Math.sqrt(ecuacion);
//
//				if (distancia < this.RADIO_VISION_ARTILLERIA) {
//					visibilidadArtilleria = true;
//				}
//				if (distancia < this.RADIO_VISION_AVION) {
//					visibilidadAvionArtilleria = true;
//				}
//
//				if(visibilidadAvionArtilleria != avionActual.isVisible()) {
//					if(!huboCambios) {
//						avionActual.setVisible(visibilidadAvionArtilleria);
//						avionDto.setVisible(visibilidadAvionArtilleria);
//						this.mensajeriaUpdate.sendAvionEnemigoVisible(avionDto.toString());
//					}
//				}
//
//				if(visibilidadArtilleria != artilleria.isVisible()) {
//					artilleria.setVisible(visibilidadArtilleria);
//					huboCambios = true;
//				}
//				listVisibilidadArtilleria.add(artilleria.getIdArtilleria(), visibilidadArtilleria);
//			}
//			res.setVisibilidadArtilleria(listVisibilidadArtilleria);
//
//			if(huboCambios) {
//				this.mensajeriaUpdate.sendElementosVisibles(res.toString());
//			}
//		}
	}

	public void updateCombustibleAviones() {
//		List<Partida> partidasEnJuego = this.manejadorPartida.
	}
}

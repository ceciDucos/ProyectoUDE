package com.proyecto.bombsaway.servicios;

import com.proyecto.bombsaway.clases.*;
import com.proyecto.bombsaway.controladores.ControladorMensajes;
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
	private final int MAX_COMBUSTIBLE = 100;
	private final int DANIO_DISPARO_BALA = 10;
	private final int DANIO_DISPARO_BALA_TORRETA = 3;
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
	private final int RADIO_VISION_BASE = 60;
	private final int RADIO_VISION_ARTILLERIA = 40;
	private final ManejadorPartida manejadorPartida;
	private final ControladorMensajes mensajeriaUpdate;

	@Autowired
	private ServicioPartidaDb servicioPartidaDb;

	@Autowired
	public ServicioPartida(ManejadorPartida manejadorPartida, ControladorMensajes mensajeriaUpdate) {
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
			avionJugadorUno.setCombustible(this.MAX_COMBUSTIBLE);
			avionJugadorUno.inicializarBalas();
			listAvionesUno.add(avionJugadorUno);

			Avion avionJugadorDos = new Avion();
			avionJugadorDos.setId(i);
			avionJugadorDos.setVida(this.MAX_VIDA);
			avionJugadorDos.setEstado(EstadoAvion.QUIETO);
			avionJugadorDos.setVisible(false);
			avionJugadorDos.setPosicion(new Posicion(540, 670, -90));
			avionJugadorDos.setCombustible(this.MAX_COMBUSTIBLE);
			avionJugadorDos.inicializarBalas();
			listAvionesDos.add(avionJugadorDos);
		}
		jugadorUno.setListAviones(listAvionesUno);
		jugadorDos.setListAviones(listAvionesDos);

		// creacion nueva partida
		return new Partida(jugadorUno, jugadorDos, nombrePartida, false);
	}

	public String unirseAPartida(DTOUsuario usuario) {
		DTOMensaje mensaje = new DTOMensaje();
		try {
			List<PartidaEnEspera> partidasEnEpera = this.manejadorPartida.getPartidasEnEspera();
			if (partidasEnEpera != null && !partidasEnEpera.isEmpty()) {
				PartidaEnEspera partidaEnEspera = partidasEnEpera.get(0);
				Partida partidaEnJuego = this.crearPartida(partidaEnEspera.getNombrePartida(),
						partidaEnEspera.getNombreJugador(), usuario.getNombreJugador());
				this.manejadorPartida.addPartidaEnJuego(partidaEnJuego);
				mensaje = new DTOMensaje("Bootloader");
				mensaje.setNombrePartida(partidaEnJuego.getNombre());
				mensaje.setCantidadMaxArtilleria(this.CANTIDAD_ARTILLERIA);
			} else {
				mensaje = new DTOMensaje("No se encuentran partidas creadas");
			}
			return mensaje.toString();
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception error) {
			error.printStackTrace();
		}
		return mensaje.toString();
	}

	public void guardarPartida(String nombrePartida) {
		try {
			Partida partida = this.recuperarPartida(nombrePartida);
			this.servicioPartidaDb.guardarPartida(partida);
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void colocarBase(DTOBase baseDto) {
		try {
			Partida partida = this.recuperarPartida(baseDto.getNombrePartida());
			if (partida != null && !partida.isFinalizada()) {
				Jugador jugadorActual = (baseDto.getIdJugador() == 1) ? partida.getJugadorUno()
						: partida.getJugadorDos();
				Posicion posicion = new Posicion(baseDto.getBaseEjeX(), baseDto.getBaseEjeY(), baseDto.getBaseEjeY());
				ElementoBase hangar = new ElementoBase("hangar",
						new Posicion(baseDto.getHangarEjeX(), baseDto.getHangarEjeY(), 0), false);
				ElementoBase torreta = new ElementoBase("torreta",
						new Posicion(baseDto.getTorretaEjeX(), baseDto.getTorretaEjeY(), 0), false);
				torreta.inicializarBalas();
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
			if (partida != null && !partida.isFinalizada()) {
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
			this.comprobarResultadoPartida(partida);

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
			if (partida != null && !partida.isFinalizada()) {
				Jugador jugadorActual = (artilleriaDto.getIdJugador() == 1) ? partida.getJugadorUno()
						: partida.getJugadorDos();
				if (jugadorActual.getListArtilleria().size() < this.CANTIDAD_ARTILLERIA) {
					Artilleria artilleria = new Artilleria(artilleriaDto.getIdArtilleria(),
							new Posicion(artilleriaDto.getEjeX(), artilleriaDto.getEjeY(), artilleriaDto.getAngulo()),
							false);
					artilleria.setVisible(false);
					artilleria.inicializarBalas();
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

		double coordX1 = bombaDto.getEjeX();
		double coordY1 = bombaDto.getEjeY();
		double coordX2 = posicion.getEjeX();
		double coordY2 = posicion.getEjeY();

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
			Partida partida = recuperarPartida(avionDTO.getNombrePartida());
			if (partida != null && !partida.isFinalizada()) {
				// si el avion se fue de los limites, estalla
				boolean avionFueraLimites = this.checkAvionFueraLimites(avionDTO);
				boolean avionSinCombustible = this.checkCombustibleAvion(avionDTO);
				DTOAvion avionChoqueDto = this.checkChoqueEntreAviones(avionDTO);

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
				this.comprobarResultadoPartida(partida);
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
		double coordX1 = posicion.getEjeX();
		double coordY1 = posicion.getEjeY();
		double coordX2 = avion.getPosicion().getEjeX();
		double coordY2 = avion.getPosicion().getEjeY();

		double ecuacion = ((coordX1 - coordX2) * (coordX1 - coordX2) + (coordY1 - coordY2) * (coordY1 - coordY2));
		distancia = Math.sqrt(ecuacion);
		if (distancia < this.RADIO_BALA + radioAvion) {
			res = true;
		}
		return res;
	}

	private DTOAvion impactarAvion(Avion avion, String elemento) {
		DTOAvion dtoAvion = null;

		if (avion.getEstado() != EstadoAvion.DESTRUIDO) {
			if (avion.getVida() > this.DANIO_DISPARO_BALA) {
				// el avion tiene suficiente vida para recibir disparo, la vida desciende
				int vidaActual = avion.getVida();
				System.out.println(elemento);

				int danio = elemento == "artilleria" ? this.DANIO_DISPARO_BALA_TORRETA : this.DANIO_DISPARO_BALA;
				System.out.println(danio);
				avion.setVida(vidaActual - danio);
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

	public DTOAvion impactoBalaArtilleriaEnAvion(DTOBala balaDto, Jugador jugadorEnemigo) {
		boolean impacto = false;
		DTOAvion dtoAvion = null;
		List<Avion> listAvionesEnemigos = jugadorEnemigo.getListAviones();
		int i = 0;
		while (!impacto && i < listAvionesEnemigos.size()) {
			Avion avion = listAvionesEnemigos.get(i);
			Posicion posicionBala = new Posicion(balaDto.getEjeX(), balaDto.getEjeY(), balaDto.getAngulo());
			// chequeamos que la bala pertenezca al radio del avion
			if (avion.getEstado() == EstadoAvion.ALTURA_BAJA) {
				impacto = this.validarImpactoRadioAvion(posicionBala, avion);
				if (impacto) {
					dtoAvion = impactarAvion(avion, "artilleria");
					dtoAvion.setNombrePartida(balaDto.getNombrePartida());
					dtoAvion.setIdJugador(jugadorEnemigo.getId());
				}
			}
			i++;
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
						dtoAvion = impactarAvion(avion, "avion");
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
			if (partida != null && !partida.isFinalizada()) {
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
				Avion avionAutorDisparo = listAvionesJugadorActual.get(balaDto.getIdElemento());
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
			this.comprobarResultadoPartida(partida);
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
			if (partida != null && !partida.isFinalizada()) {
				Jugador jugadorActual = null;
				if (balaDto.getIdJugador() == 1) {
					jugadorActual = partida.getJugadorUno();
				} else {
					jugadorActual = partida.getJugadorDos();
				}
				List<Avion> listAvionesJugadorActual = jugadorActual.getListAviones();
				Avion avionAutorDisparo = listAvionesJugadorActual.get(balaDto.getIdElemento());
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

	private void comprobarResultadoPartida(Partida partida) {
		try {
			if (partida != null && !partida.isFinalizada()) {
				if (!partida.isFinalizada()) {
					boolean avionesConVidaJ1 = false;
					boolean avionesConVidaJ2 = false;
					List<Avion> avionesJ1 = partida.getJugadorUno().getListAviones();
					List<Avion> avionesJ2 = partida.getJugadorDos().getListAviones();
					int i = 0;
					while (i < avionesJ1.size() && !avionesConVidaJ1) {
						Avion avionJ1 = avionesJ1.get(i);
						if (avionJ1.getEstado() != EstadoAvion.DESTRUIDO) {
							avionesConVidaJ1 = true;
						}
						i++;
					}
					i = 0;
					while (i < avionesJ2.size() && !avionesConVidaJ2) {
						Avion avionJ2 = avionesJ2.get(i);
						if (avionJ2.getEstado() != EstadoAvion.DESTRUIDO) {
							avionesConVidaJ2 = true;
						}
						i++;
					}
					if (!avionesConVidaJ1 || !avionesConVidaJ2) {
						DTOResultadoPartida resultadoPartidaDto = new DTOResultadoPartida(partida.getNombre(),
								avionesConVidaJ1, avionesConVidaJ2);
						partida.setFinalizada(true);
						this.manejadorPartida.updatePartidaEnJuego(partida);
						this.mensajeriaUpdate.sendResultadoPartida(resultadoPartidaDto.toString());
					}
				}
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public void dispararBalaArtilleria(DTOBala balaDto) {
		try {
			Partida partida = this.recuperarPartida(balaDto.getNombrePartida());
			if (partida != null && !partida.isFinalizada()) {
				Jugador jugadorEnemigo = balaDto.getIdJugador() == 1 ? partida.getJugadorDos()
						: partida.getJugadorUno();
				Jugador jugadorActual = balaDto.getIdJugador() == 1 ? partida.getJugadorUno() : partida.getJugadorDos();

				List<Artilleria> listArtilleriaJugadorActual = jugadorActual.getListArtilleria();
				Artilleria artilleriaAutoraDisparo = listArtilleriaJugadorActual.get(balaDto.getIdElemento());
				Bala balaDisparada = artilleriaAutoraDisparo.getListBalas().get(balaDto.getIdBala());
				if (balaDisparada.isVisible()) {
					// se obtienen los aviones del enemigo para chequear si impacto o no
					DTOAvion dtoAvion = this.impactoBalaArtilleriaEnAvion(balaDto, jugadorEnemigo);
					if (dtoAvion != null) {
						// si la bala impacto contra avion enemigo
						if (dtoAvion.getEstado() == EstadoAvion.DESTRUIDO) {
							this.estallarAvion(dtoAvion.toString());
						} else {
							this.bajarVidaAvion(dtoAvion.toString());
						}
						balaDisparada.setVisible(false);
						balaDto.setVisible(false);
						this.mensajeriaUpdate.sendPosicionBalaArtilleria(balaDto.toString());
					} else {
						// se actualiza la posicion de la bala y se avisa a los clientes
						balaDisparada
								.setPosicion(new Posicion(balaDto.getEjeX(), balaDto.getEjeY(), balaDto.getAngulo()));
						// chequeamos que si la visibilidad de la bala cambio, si cambio notificamos y
						// actualizamos la bala
						if (!balaDto.isVisible()) {
							balaDisparada.setVisible(balaDto.isVisible());
							this.mensajeriaUpdate.sendPosicionBalaArtilleria(balaDto.toString());
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

	public void primerDisparoBalaArtilleria(DTOBala balaDto) {
		try {
			Partida partida = this.recuperarPartida(balaDto.getNombrePartida());
			if (partida != null && !partida.isFinalizada()) {
				Jugador jugadorActual = balaDto.getIdJugador() == 1 ? partida.getJugadorUno() : partida.getJugadorDos();

				List<Artilleria> listArtilleriaJugadorActual = jugadorActual.getListArtilleria();
				Artilleria artilleriaAutoraDisparo = listArtilleriaJugadorActual.get(balaDto.getIdElemento());
				Bala balaDisparada = artilleriaAutoraDisparo.getListBalas().get(balaDto.getIdBala());
				balaDisparada.setVisible(true);
				this.mensajeriaUpdate.sendPosicionBalaArtilleria(balaDto.toString());
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
			System.out.println("Error: " + error.getMensaje());
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public void primerDisparoBalaTorre(DTOBala balaDto) {
		try {
			Partida partida = this.recuperarPartida(balaDto.getNombrePartida());
			if (partida != null && !partida.isFinalizada()) {
				Jugador jugadorActual = balaDto.getIdJugador() == 1 ? partida.getJugadorUno() : partida.getJugadorDos();
				List<ElementoBase> listElementosBase = jugadorActual.getBase().getElementosBase();
				ElementoBase torre = null;
				for (ElementoBase elementoBase : listElementosBase) {
					if (elementoBase.getNombre().equalsIgnoreCase("torreta")) {
						torre = elementoBase;
					}
				}
				if (torre != null) {
					Bala balaDisparada = torre.getListBalas().get(balaDto.getIdBala());
					balaDisparada.setVisible(true);
					this.mensajeriaUpdate.sendPosicionBalaArtilleria(balaDto.toString());
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

	public void dispararBalaTorre(DTOBala balaDto) {
		try {
			Partida partida = this.recuperarPartida(balaDto.getNombrePartida());
			if (partida != null && !partida.isFinalizada()) {
				Jugador jugadorEnemigo = balaDto.getIdJugador() == 1 ? partida.getJugadorDos()
						: partida.getJugadorUno();
				Jugador jugadorActual = balaDto.getIdJugador() == 1 ? partida.getJugadorUno() : partida.getJugadorDos();
				List<ElementoBase> listElementosBase = jugadorActual.getBase().getElementosBase();
				ElementoBase torre = null;
				for (ElementoBase elementoBase : listElementosBase) {
					if (elementoBase.getNombre().equalsIgnoreCase("torreta")) {
						torre = elementoBase;
					}
				}
				Bala balaDisparada = null;
				if (torre != null) {
					balaDisparada = torre.getListBalas().get(balaDto.getIdBala());
				}

				if (balaDisparada != null && balaDisparada.isVisible()) {
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
						this.mensajeriaUpdate.sendPosicionBalaTorre(balaDto.toString());
					} else {
						// se actualiza la posicion de la bala y se avisa a los clientes
						balaDisparada
								.setPosicion(new Posicion(balaDto.getEjeX(), balaDto.getEjeY(), balaDto.getAngulo()));
						// chequeamos que si la visibilidad de la bala cambio, si cambio notificamos y
						// actualizamos la bala
						if (!balaDto.isVisible()) {
							balaDisparada.setVisible(balaDto.isVisible());
							this.mensajeriaUpdate.sendPosicionBalaTorre(balaDto.toString());
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
		double coordX1 = posicionAvionActual.getEjeX();
		double coordY1 = posicionAvionActual.getEjeY();
		double coordX2 = posicionAvionEnemigo.getEjeX();
		double coordY2 = posicionAvionEnemigo.getEjeY();
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
		if (partida != null && !partida.isFinalizada()) {
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
		if (partida != null && !partida.isFinalizada()) {
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
			if (partida != null && !partida.isFinalizada()) {
				Jugador jugadorActual = (artilleriaDto.getIdJugador() == 1) ? partida.getJugadorUno()
						: partida.getJugadorDos();
				Artilleria artilleria = jugadorActual.getListArtilleria().get(artilleriaDto.getIdArtilleria());
				artilleria.setPosicion(
						new Posicion(artilleriaDto.getEjeX(), artilleriaDto.getEjeY(), artilleriaDto.getAngulo()));
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

	private boolean checkVisibilidad(Avion avionDto, String elemento, Posicion posicion) {
		boolean res = false;
		double coordX1 = avionDto.getPosicion().getEjeX();
		double coordY1 = avionDto.getPosicion().getEjeY();
		double coordX2 = posicion.getEjeX();
		double coordY2 = posicion.getEjeY();
		int radioElemento = 0;
		switch (elemento) {
		case "avion": {
			radioElemento = this.RADIO_VISION_AVION;
			break;
		}
		case "base": {
			radioElemento = this.RADIO_VISION_BASE;
			break;
		}
		case "artilleria": {
			radioElemento = this.RADIO_ARTILLERIA;
			break;
		}
		default:
			break;
		}

		double ecuacion = ((coordX1 - coordX2) * (coordX1 - coordX2) + (coordY1 - coordY2) * (coordY1 - coordY2));
		double distancia = Math.sqrt(ecuacion);
		if (distancia < radioElemento + this.RADIO_VISION_AVION) {
			res = true;
		}
		return res;
	}

	public DTOVisibilidad initDTOVisibilidad(String nombrePartida, int idJugador) {
		DTOVisibilidad res = new DTOVisibilidad();
		res.setNombrePartida(nombrePartida);
		res.setIdJugador(idJugador);
		res.setVisibilidadBase(false);

		List<Boolean> listVisibilidadAviones = new ArrayList<Boolean>();
		for (int i = 0; i < 4; i++) {
			listVisibilidadAviones.add(i, false);
		}
		List<Boolean> listVisibilidadArtilleria = new ArrayList<Boolean>();
		for (int i = 0; i < 11; i++) {
			listVisibilidadArtilleria.add(i, false);
		}
		res.setVisibilidadAviones(listVisibilidadAviones);
		res.setVisibilidadArtilleria(listVisibilidadArtilleria);

		return res;
	}

	public void updateVisibilidad3(int idJugador) {
		DTOVisibilidad res = null;
		try {
			List<Partida> partidas = this.manejadorPartida.getPartidasEnJuego();
			for (Partida partida : partidas) {
				if (!partida.isFinalizada()) {
					Jugador jugadorActual = idJugador == 1 ? partida.getJugadorUno() : partida.getJugadorDos();
					Jugador jugadorEnemigo = idJugador == 1 ? partida.getJugadorDos() : partida.getJugadorUno();
					boolean jugadorActualPronto = false;
					boolean jugadorEnemigoPronto = false;

					if (jugadorActual.getListArtilleria() != null
							&& jugadorActual.getListArtilleria().size() == this.CANTIDAD_ARTILLERIA) {
						jugadorActualPronto = true;
					}

					if (jugadorEnemigo.getListArtilleria() != null
							&& jugadorEnemigo.getListArtilleria().size() == this.CANTIDAD_ARTILLERIA) {
						jugadorEnemigoPronto = true;
					}

					if (jugadorEnemigoPronto && jugadorActualPronto) {
						res = initDTOVisibilidad(partida.getNombre(), jugadorActual.getId());

						// elementos de jugador actual que pueden cambiar la visibilidad
						List<Avion> avionesActuales = jugadorActual.getListAviones();
						List<Artilleria> artilleriasActual = jugadorActual.getListArtilleria();
						Base baseActual = jugadorActual.getBase();

						// elementos del enemigo a chequear
						Base baseEnemigo = jugadorEnemigo.getBase();
						List<Avion> listAvionesEnemigo = jugadorEnemigo.getListAviones();
						List<Artilleria> listArtilleriaEnemigo = jugadorEnemigo.getListArtilleria();

						int i = 0;
						while (i < avionesActuales.size()) {
							Avion avion = avionesActuales.get(i);
							if (avion.getEstado() == EstadoAvion.ALTURA_BAJA
									|| avion.getEstado() == EstadoAvion.ALTURA_ALTA) {
								boolean esVisibleBase = this.checkVisibilidad(avion, "base", baseEnemigo.getPosicion());
								if (esVisibleBase) {
									res.setVisibilidadBase(true);
								}

								for (Avion avionEnemigo : listAvionesEnemigo) {
									if (avionEnemigo.getEstado() == EstadoAvion.ALTURA_BAJA
											|| avionEnemigo.getEstado() == EstadoAvion.ALTURA_ALTA) {
										Boolean visibilidadAvion = this.checkVisibilidad(avion, "avion",
												avionEnemigo.getPosicion());
										if (visibilidadAvion) {
											if (res.getVisibilidadAviones().get(avionEnemigo.getId()) != null) {
												res.getVisibilidadAviones().remove(avionEnemigo.getId());
											}
											res.getVisibilidadAviones().add(avionEnemigo.getId(), true);
										}
									}
								}

								for (Artilleria artilleriaEnemigo : listArtilleriaEnemigo) {
									if (!artilleriaEnemigo.isDestruida()
											&& (avion.getEstado() == EstadoAvion.ALTURA_ALTA
													|| avion.getEstado() == EstadoAvion.ALTURA_BAJA)) {
										Boolean visibilidadAvionArtilleria = this.checkVisibilidad(avion, "artilleria",
												artilleriaEnemigo.getPosicion());
										if (visibilidadAvionArtilleria) {
											res.getVisibilidadArtilleria().add(artilleriaEnemigo.getIdArtilleria(),
													true);
										}
									}
								}
							}
							i++;
						}

						int j = 0;
						while (j < artilleriasActual.size()) {
							Artilleria artilleria = artilleriasActual.get(j);

							if (!artilleria.isDestruida()) {
								for (Avion avionEnemigo : listAvionesEnemigo) {
									if (avionEnemigo.getEstado() == EstadoAvion.ALTURA_BAJA
											|| avionEnemigo.getEstado() == EstadoAvion.ALTURA_ALTA) {
										boolean avionBajoArtilleria = this.avionBajoRadioArtilleria(avionEnemigo,
												artilleria.getPosicion());
										if (avionBajoArtilleria) {
											if (res.getVisibilidadAviones().get(avionEnemigo.getId()) != null) {
												res.getVisibilidadAviones().remove(avionEnemigo.getId());
											}
											res.getVisibilidadAviones().add(avionEnemigo.getId(), true);
										}
									}
								}
							}
							j++;
						}

						for (Avion avionEnemigo : listAvionesEnemigo) {
							if (avionEnemigo.getEstado() == EstadoAvion.ALTURA_BAJA
									|| avionEnemigo.getEstado() == EstadoAvion.ALTURA_ALTA) {
								Boolean visibilidadAvion = this.checkVisibilidad(avionEnemigo, "base",
										baseActual.getPosicion());
								if (visibilidadAvion) {
									if (res.getVisibilidadAviones().get(avionEnemigo.getId()) != null) {
										res.getVisibilidadAviones().remove(avionEnemigo.getId());
									}
									res.getVisibilidadAviones().add(avionEnemigo.getId(), true);
								}
							}
						}

						this.updateVisibilidadPartida(res);
						if (res != null) {
							this.mensajeriaUpdate.sendActualizacionElementosVisibles(res.toString());
						}
					}
				}
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateVisibilidadPartida(DTOVisibilidad visibilidad) throws ConcurrenciaException {
		if (visibilidad != null) {
			Partida partida = this.recuperarPartida(visibilidad.getNombrePartida());
			if (partida != null && !partida.isFinalizada()) {
				Jugador jugadorEnemigo = visibilidad.getIdJugador() == 1 ? partida.getJugadorDos()
						: partida.getJugadorUno();

				// actualizo visibilidad base
				jugadorEnemigo.getBase().setVisible(visibilidad.isVisibilidadBase());

				// actualizo visibilidad aviones
				List<Avion> listAvionesJugadorEnemigo = jugadorEnemigo.getListAviones();
				int i = 0;
				while (i < listAvionesJugadorEnemigo.size()) {
					jugadorEnemigo.getListAviones().get(i).setVisible(visibilidad.getVisibilidadAviones().get(i));
					i++;
				}

				// actualizo visibilidad artilleria
				List<Artilleria> listArtillriaJugadorEnemigo = jugadorEnemigo.getListArtilleria();
				int j = 0;
				while (j < listArtillriaJugadorEnemigo.size()) {
					jugadorEnemigo.getListArtilleria().get(i).setVisible(visibilidad.getVisibilidadArtilleria().get(i));
					j++;
				}
			}
		}
	}

	public boolean avionBajoRadioArtilleria(Avion avion, Posicion posicionArtilleria) {
		boolean res = false;
		double coordX1 = avion.getPosicion().getEjeX();
		double coordY1 = avion.getPosicion().getEjeY();
		double coordX2 = posicionArtilleria.getEjeX();
		double coordY2 = posicionArtilleria.getEjeY();
		double ecuacion = ((coordX1 - coordX2) * (coordX1 - coordX2) + (coordY1 - coordY2) * (coordY1 - coordY2));
		double distancia = Math.sqrt(ecuacion);

		if (distancia < this.RADIO_VISION_ARTILLERIA + this.RADIO_VISION_AVION) {
			res = true;
		}
		return res;
	}

	private String getStringCombustible(String nombrePartida, int idJugador, List<Integer> listCombustible) {
		String data = "{\"nombrePartida\":\"" + nombrePartida + "\",\"idJugador\":" + idJugador
				+ ",\"listCombustibles\": [";
		int i = 0;
		while (i < listCombustible.size()) {
			data += listCombustible.get(i);
			i++;
			if (i < listCombustible.size()) {
				data += ",";
			}
		}
		data += "]}";
		return data;
	}

	public void recargarCombustibleAvion(DTOAvion avionDto) {
		try {
			Partida partida = this.manejadorPartida.getPartidaEnJuego(avionDto.getNombrePartida());
			if (partida != null && !partida.isFinalizada()) {
				Jugador jugadorActual = avionDto.getIdJugador() == 1 ? partida.getJugadorUno()
						: partida.getJugadorDos();
				Avion avion = jugadorActual.getListAviones().get(avionDto.getIdAvion());
				if (avion.getEstado() == EstadoAvion.ALTURA_ALTA || avion.getEstado() == EstadoAvion.ALTURA_BAJA) {
					avion.setCombustible(this.MAX_COMBUSTIBLE);
				}
			}
		} catch (ConcurrenciaException error) {
			String mensajeError = this.getMensajeError(error.getMensaje());
			this.mensajeriaUpdate.sendErrores(mensajeError);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateCombustibleAviones() {
		List<Partida> partidasEnJuego = this.manejadorPartida.getPartidasEnJuego();
		for (Partida partida : partidasEnJuego) {
			if (partida != null && !partida.isFinalizada()) {
				Jugador jugadorUno = partida.getJugadorUno();
				Jugador jugadorDos = partida.getJugadorDos();

				boolean jugadorUnoPronto = false;
				boolean jugadorDosPronto = false;

				if (jugadorUno.getListArtilleria() != null
						&& jugadorUno.getListArtilleria().size() == this.CANTIDAD_ARTILLERIA) {
					jugadorUnoPronto = true;
				}

				if (jugadorDos.getListArtilleria() != null
						&& jugadorDos.getListArtilleria().size() == this.CANTIDAD_ARTILLERIA) {
					jugadorDosPronto = true;
				}

				if (jugadorUnoPronto && jugadorDosPronto) {
					List<Avion> avionesJugadorUno = jugadorUno.getListAviones();
					List<Avion> avionesJugadorDos = jugadorDos.getListAviones();

					List<Integer> listCombustibleJugadorUno = new ArrayList<Integer>();
					List<Integer> listCombustibleJugadorDos = new ArrayList<Integer>();

					for (Avion avionJugadorUno : avionesJugadorUno) {
						if (avionJugadorUno.getEstado() == EstadoAvion.ALTURA_BAJA
								|| avionJugadorUno.getEstado() == EstadoAvion.ALTURA_ALTA) {
							int combustibleActual = avionJugadorUno.getCombustible();
							avionJugadorUno.setCombustible(combustibleActual - 1);
						}
						listCombustibleJugadorUno.add(avionJugadorUno.getId(), avionJugadorUno.getCombustible());
					}

					for (Avion avionJugadorDos : avionesJugadorDos) {
						if (avionJugadorDos.getEstado() == EstadoAvion.ALTURA_BAJA
								|| avionJugadorDos.getEstado() == EstadoAvion.ALTURA_ALTA) {
							int combustibleActual = avionJugadorDos.getCombustible();
							avionJugadorDos.setCombustible(combustibleActual - 1);
						}
						listCombustibleJugadorDos.add(avionJugadorDos.getId(), avionJugadorDos.getCombustible());
					}

					String dataJugadorUno = this.getStringCombustible(partida.getNombre(), 1,
							listCombustibleJugadorUno);
					this.mensajeriaUpdate.sendCombustibleAvion(dataJugadorUno);
					String dataJugadorDos = this.getStringCombustible(partida.getNombre(), 2,
							listCombustibleJugadorDos);
					this.mensajeriaUpdate.sendCombustibleAvion(dataJugadorDos);
				}

			}
		}
	}

}

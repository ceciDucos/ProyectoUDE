package com.proyecto.bombsaway.servicios;

import com.proyecto.bombsaway.clases.*;
import com.proyecto.bombsaway.config.SchedulerConfig;
import com.proyecto.bombsaway.daos.IDAOPartida;
import com.proyecto.bombsaway.dtos.*;
import com.proyecto.bombsaway.manejadores.ManejadorPartida;
import com.proyecto.bombsaway.enumerados.EstadoAvion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioPartida {
    private final int MAX_VIDA = 100;
    private final int DANIO_DISPARO_BALA = 10;
    private final int RADIO_AVION_ALTURA_ALTA = 25;
    private final int RADIO_AVION_ALTURA_BAJA = 19;
    private final int RADIO_BALA = 1;
    private final ManejadorPartida manejadorPartida;
    private final SchedulerConfig mensajeriaUpdate;
    private final IDAOPartida DAOPartida;

    @Autowired
    public ServicioPartida(ManejadorPartida manejadorPartida, IDAOPartida DAOPartida, SchedulerConfig mensajeriaUpdate) {
        this.DAOPartida = DAOPartida;
        this.manejadorPartida = manejadorPartida;
        this.mensajeriaUpdate = mensajeriaUpdate;
    }

    public int crearPartidaEnEspera(DTOPartidaEnEspera partidaEnEspera) {
        PartidaEnEspera nuevaPartida = new PartidaEnEspera(partidaEnEspera.getNombrePartida(), partidaEnEspera.getNombreJugador());
        this.manejadorPartida.addPartidaEnEspera(nuevaPartida);
        return 1;
    }

    private Partida crearPartida(String nombrePartida, String nombreJugadorUno, String nombreJugadorDos) {
        Jugador jugadorUno = new Jugador(nombreJugadorUno);
        jugadorUno.setId(1);
        Jugador jugadorDos = new Jugador(nombreJugadorDos);
        jugadorUno.setId(2);
        List<Avion> listAvionesUno = new ArrayList<Avion>();
        List<Avion> listAvionesDos = new ArrayList<Avion>();

        //inicializacion aviones de cada jugador
        for( int i = 0; i <= 3; i++) {
            Avion avionJugadorUno = new Avion();
            avionJugadorUno.setId(i);
            avionJugadorUno.setPosicion( new Posicion( 540, 50, 90));
            avionJugadorUno.inicializarBalas();
            System.out.println("lista balas inicializadas j1: ");
            System.out.println(avionJugadorUno.getListBalas());
            listAvionesUno.add(avionJugadorUno);

            Avion avionJugadorDos = new Avion();
            avionJugadorDos.setId(i);
            avionJugadorDos.setPosicion(new Posicion( 540, 670, -90));
            avionJugadorDos.inicializarBalas();
            System.out.println("lista balas inicializadas j2: ");
            System.out.println(avionJugadorDos.getListBalas());
            listAvionesDos.add(avionJugadorDos);
        }
        jugadorUno.setListAviones(listAvionesUno);
        jugadorDos.setListAviones(listAvionesDos);

        //creacion nueva partida
        return new Partida(jugadorUno, jugadorDos, nombrePartida);
    }

    public DTOMensaje unirseAPartida(DTOUsuario usuario) {
        List<PartidaEnEspera> partidasEnEpera = this.manejadorPartida.getPartidasEnEspera();
        if(partidasEnEpera != null && !partidasEnEpera.isEmpty()) {
            PartidaEnEspera partidaEnEspera = partidasEnEpera.get(0);
            Partida partidaEnJuego = this.crearPartida(partidaEnEspera.getNombrePartida(),
                    partidaEnEspera.getNombreJugador(),
                    usuario.getNombreJugador());
            this.manejadorPartida.addPartidaEnJuego(partidaEnJuego);
            return new DTOMensaje("Bootloader");
        }
        return new DTOMensaje("No se encuentran partidas creadas");
    }

    public List<DTOPartidaCompleto> getListPartidas() {
        List<Partida> listPartidas = this.DAOPartida.getListPartidas();
        List<DTOPartidaCompleto> listPartidasDTOS = new ArrayList<DTOPartidaCompleto>();
        for (Partida partida: listPartidas ) {
            DTOPartidaCompleto partidaDTO = partida.getDTOCompleto();
            listPartidasDTOS.add(partidaDTO);
        }
        return listPartidasDTOS;
    }

    public void moverAvion(DTOAvion avionDTO) {
        try {
            String notificacion = null;
            //si el avion se fue de los limites, estalla
            boolean estallarAvion = this.checkAvionFueraLimites(avionDTO);
            Partida partida = recuperarPartida(avionDTO.getNombrePartida());
            if(estallarAvion) {
                avionDTO.setEstado(EstadoAvion.DESTRUIDO);
                //se actualiza la partida y se envia el avion a estallar
                notificacion = this.updateAvionEnPartida(avionDTO, partida);
                this.estallarAvion(notificacion.toString());
            } else {
                //se actualiza la partida y se envia el status del avion a el canal aviones-enemigos
                notificacion = this.updateAvionEnPartida(avionDTO, partida);
                this.mensajeriaUpdate.sendAvionesEnemigos(notificacion.toString());
            }
        } catch (Exception error) {
            String mensajeError = this.getMensajeError(error.getMessage());
            System.out.println("error: " + error.getMessage());
            this.mensajeriaUpdate.sendPosicionBala(mensajeError);
        }
    }

    private void estallarAvion(String data) {
        this.mensajeriaUpdate.sendEstallarAviones(data);
    }

    private boolean validarImpactoRadioAvion(Posicion posicion, Avion avion){
        boolean res = false;
        double distancia = 0;
        int radioAvion;
        if(avion.getEstado() == EstadoAvion.ALTURA_ALTA) {
            radioAvion = this.RADIO_AVION_ALTURA_ALTA;
        } else {
            radioAvion = this.RADIO_AVION_ALTURA_BAJA;
        }
        int coordX1 = posicion.getEjeX();
        int coordY1 = posicion.getEjeY();
        int coordX2 = avion.getPosicion().getEjeX();
        int coordY2 = avion.getPosicion().getEjeX();

        double ecuacion = ((coordX1 - coordX2)*(coordX1 - coordX2) + (coordY1 - coordY2)*(coordY1 - coordY2));
        distancia = Math.sqrt(ecuacion);

        if(distancia < this.RADIO_BALA + radioAvion) {
            res = true;
        }
        return res;
    }

    //fijarse si esta funcion puede ser pasada al avion.
    private DTOAvion impactarAvion(Avion avion) {
        DTOAvion dtoAvion = null;
        if(avion.getVida() > this.DANIO_DISPARO_BALA) {
            //el avion tiene suficiente vida para recibir disparo, la vida desciende
            int vidaActual = avion.getVida();
            avion.setVida(vidaActual - this.DANIO_DISPARO_BALA);
            dtoAvion = avion.getDTO();
        } else {
            //el avion tiene poca vida y el disparo lo hace estallar
            avion.setEstado(EstadoAvion.DESTRUIDO);
            avion.setVida(0);
            dtoAvion = avion.getDTO();
        }
        return dtoAvion;
    }

    public DTOAvion impactoBala(DTOBala balaDto, Jugador jugadorEnemigo) {
        boolean impacto = false;
        DTOAvion dtoAvion = null;
        try{
            List<Avion> listAvionesEnemigos = jugadorEnemigo.getListAviones();
            int i = 0;
            while(!impacto && i< listAvionesEnemigos.size()) {
                Avion avion = listAvionesEnemigos.get(i);
                if(avion.getEstado() == balaDto.getAltitud()) {
                    Posicion posicionBala = new Posicion(balaDto.getEjeX(),
                            balaDto.getEjeY(),balaDto.getAngulo());
                    //chequeamos que la bala pertenezca al radio del avion
                    impacto = this.validarImpactoRadioAvion(posicionBala, avion);
                    if(impacto) {
                        dtoAvion = impactarAvion(avion);
                        dtoAvion.setNombrePartida(balaDto.getNombrePartida());
                        dtoAvion.setIdJugador(jugadorEnemigo.getId());
                    }
                }
                i++;
            }
        } catch (Exception error) {
            String mensajeError = this.getMensajeError(error.getMessage());
            System.out.println("error: " + error.getMessage());
            this.mensajeriaUpdate.sendPosicionBala(mensajeError);
        }
        return dtoAvion;
    }

    public void dispararBala(DTOBala balaDto) {
        try {
            System.out.println("el dto que llega: " + balaDto.toString());
            Partida partida = this.recuperarPartida(balaDto.getNombrePartida());
            if(partida != null) {
                Jugador jugadorEnemigo = null;
                Jugador jugadorActual = null;
                if(balaDto.getIdJugador() == 1) {
                    jugadorEnemigo = partida.getJugadorDos();
                    jugadorActual =  partida.getJugadorUno();
                } else {
                    jugadorEnemigo = partida.getJugadorUno();
                    jugadorActual =  partida.getJugadorDos();
                }

                List<Avion> listAvionesJugadorActual = jugadorActual.getListAviones();
                Avion avionAutorDisparo = listAvionesJugadorActual.get(balaDto.getIdAvion());
                List<Bala> listaBalas = avionAutorDisparo.getListBalas();
                Bala balaDisparada = avionAutorDisparo.getListBalas().get(balaDto.getIdBala());

                //se obtienen los aviones del enemigo para chequear si impacto o no

                List<Avion> listAvionesEnemigos = jugadorEnemigo.getListAviones();
                DTOAvion dtoAvion = this.impactoBala(balaDto, jugadorEnemigo);

                if(dtoAvion!= null ) {
                    System.out.println("bala impacto en avion de id: " + dtoAvion.getIdAvion());
                    //si la bala impacto contra avion enemigo
                    if(dtoAvion.getEstado() == EstadoAvion.DESTRUIDO){
                        this.estallarAvion(dtoAvion.toString());
                    } else {
                        this.bajarVidaAvion(dtoAvion.toString());
                    }
                    balaDisparada.setVisible(false);
                    balaDto.setVisible(false);
                    this.mensajeriaUpdate.sendPosicionBala(balaDto.toString());
                } else {
                    System.out.println("bala no impacto en nada");
                    //se actualiza la posicion de la bala y se avisa a los clientes
                    balaDisparada.setPosicion(new Posicion(balaDto.getEjeX(), balaDto.getEjeY(), balaDto.getAngulo()));
                    //chequeamos que si la visibilidad de la bala cambio, si cambio notificamos y actualizamos la bala
                    if(balaDisparada.isVisible() != balaDto.isVisible()) {
                        this.mensajeriaUpdate.sendPosicionBala(balaDto.toString());
                        balaDisparada.setVisible(balaDto.isVisible());
                    }
                }
            }
        } catch (Exception error) {
            String mensajeError = this.getMensajeError(error.getMessage());
            System.out.println("error: " + error.getMessage());
            this.mensajeriaUpdate.sendPosicionBala(mensajeError);
        }
    }

    private String getMensajeError(String mensajeError) {
        String res = "{\"error\":\"" + mensajeError + "}";
        return res;
    }

    private void bajarVidaAvion(String data) {
        this.mensajeriaUpdate.sendBajarVidaAvion(data);
    }

    private Partida recuperarPartida(String nombrePartida) {
        return this.manejadorPartida.getPartidaEnJuego(nombrePartida);
    }

    private boolean checkAvionFueraLimites(DTOAvion dtoAvion) {
        boolean res = false;
        if(dtoAvion.getEjeX() <= 0 || dtoAvion.getEjeX() >= 1080 || dtoAvion.getEjeY() <= 0 || dtoAvion.getEjeY() > 720) {
            res = true;
        }
        return res;
    }

    private String updateAvionEnPartida(DTOAvion avionDTO, Partida partida) {
        DTOAvion notificacion = null;
        if (partida != null) {
            Jugador jugador = null;
            Avion avion = null;
            if (avionDTO.getIdJugador() == 1) {
                jugador = partida.getJugadorUno();
                avion = partida.getJugadorUno().getListAviones().get(avionDTO.getIdAvion());

                //se crea la notificacion para dibujar el avion enemigo
                notificacion = new DTOAvion(avionDTO);

                //actualizar avion, en usuario de partida
                avion.updateAvion(avionDTO);
                jugador.getListAviones().add(avion);
                partida.setJugadorUno(jugador);
            } else {
                jugador = partida.getJugadorDos();
                avion = partida.getJugadorDos().getListAviones().get(avionDTO.getIdAvion());

                //se crea la notificacion para dibujar el avion enemigo
                notificacion = new DTOAvion(avionDTO);

                //actualizar avion, en usuario de partida
                jugador.getListAviones().remove(avion);
                avion.updateAvion(avionDTO);
                jugador.getListAviones().add(avion);
                partida.setJugadorUno(jugador);
            }
            this.manejadorPartida.updatePartidaEnJuego(partida);
        }
        return notificacion.toString();
    }

//    public void runTests() {
//        //partida en espera
//        String nombrePartida = "partida de test";
//        String nombreJugadorUno = "Juan";
//        DTOPartidaEnEspera dtoPartidaEnEspera = new DTOPartidaEnEspera(nombrePartida, nombreJugadorUno);
//        //unirse a partida
//        String nombreJugadorDos = "Lara";
//        DTOUsuario jugadorAUnirse = new DTOUsuario(nombreJugadorDos);
//        this.unirseAPartida(jugadorAUnirse);
//
//        //mover avion jugador 1 a una posicion particular
//        DTOAvion avion = new DTOAvion();
//        avion.setNombrePartida("partida de test");
//        avion.setIdJugador(1);
//        avion.setIdAvion(1);
//        avion.setEstado(EstadoAvion.ALTURA_ALTA);
//        avion.setAngulo(30);
//        avion.setVida(40);
//        avion.setCombustible(30);
//        avion.setTieneBomba(false);
//        this.moverAvion(avion);
//
//        //mover avion jugador 1 a una posicion particular
//
//
//        //disparar bala
//        DTOBala dtoBala = new DTOBala();
//        dispararBala();
//    }
}
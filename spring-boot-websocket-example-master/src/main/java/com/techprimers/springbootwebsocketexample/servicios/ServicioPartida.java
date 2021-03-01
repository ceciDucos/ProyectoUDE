package com.techprimers.springbootwebsocketexample.servicios;

import com.techprimers.springbootwebsocketexample.clases.*;
import com.techprimers.springbootwebsocketexample.config.SchedulerConfig;
import com.techprimers.springbootwebsocketexample.daos.IDAOPartida;
import com.techprimers.springbootwebsocketexample.dtos.*;
import com.techprimers.springbootwebsocketexample.manejadores.ManejadorPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioPartida {
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
        System.out.println("nombreJugadorDos" + nombreJugadorDos);
        Jugador jugadorUno = new Jugador(nombreJugadorUno);
        jugadorUno.setId(1);
        Jugador jugadorDos = new Jugador(nombreJugadorDos);
        jugadorUno.setId(2);
        List<Avion> listAvionesUno = new ArrayList<Avion>();
        List<Avion> listAvionesDos = new ArrayList<Avion>();

        //inicializacion aviones de cada jugador
        for( int i = 1; i <= 4; i++) {
            Avion avionJugadorUno = new Avion();
            avionJugadorUno.setId(i);
            avionJugadorUno.setPosicion( new Posicion( 540, 50, 90));
            listAvionesUno.add(avionJugadorUno);

            Avion avionJugadorDos = new Avion();
            avionJugadorDos.setId(i);
            avionJugadorDos.getId();
            avionJugadorDos.setPosicion(new Posicion( 540, 670, -90));
            listAvionesDos.add(avionJugadorDos);
        }
        jugadorUno.setListAviones(listAvionesUno);
        jugadorDos.setListAviones(listAvionesDos);

        //creacion nueva partida
        return new Partida(jugadorUno, jugadorDos, nombrePartida);
    }

    public DTOMensaje unirseAPartida(DTOUsuario usuario) {
        System.out.println("nombre del usuario que llega: " +  usuario.getNombreJugador());
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

    private JsonObject partidaToJSON (Partida partida) {
        System.out.println(partida.toString());
        return Json.createObjectBuilder()
                .add("nombrePartida", partida.getNombre())
                .add("jugadorUno", partida.getJugadorUno().toJSON())
                .add("jugadorDos", partida.getJugadorDos().toJSON())
                .build();
    }

    public List<DTOPartidaCompleto> getListPartidas() {
        List<Partida> listPartidas = this.DAOPartida.getListPartidas();
        List<DTOPartidaCompleto> listPartidasDTOS = new ArrayList<DTOPartidaCompleto>();
        for (Partida partida: listPartidas ) {
            System.out.println(partida.toString());
            DTOPartidaCompleto partidaDTO = partida.getDTOCompleto();
            listPartidasDTOS.add(partidaDTO);
        }
        return listPartidasDTOS;
    }

    private Partida recuperarPartida(String nombrePartida) {
        return this.manejadorPartida.getPartidaEnJuego(nombrePartida);
    }

    private Avion updateAvion(DTOAvion dtoAvion) {
        Avion avion = new Avion();
        avion.setId(dtoAvion.getIdAvion());
        avion.setPosicion(new Posicion(dtoAvion.getEjeX(), dtoAvion.getEjeY(), dtoAvion.getAngulo()));
        avion.setVisible(true);
        avion.setVida(dtoAvion.getVida());
        avion.setCombustible(dtoAvion.getCombustible());
        avion.setEstado(dtoAvion.getEstado());
        avion.setTieneBomba(dtoAvion.isTieneBomba());
        return avion;
    }

    private boolean checkAvionFueraLimites(DTOAvion dtoAvion) {
        boolean res = false;
        if(dtoAvion.getEjeX() <= -12 || dtoAvion.getEjeX() >= 193 || dtoAvion.getEjeY() <= -17 || dtoAvion.getEjeY() > 737) {
            res = true;
        }
        return res;
    }

    private String updateAvionEnPartida(DTOAvion avionDTO) {
        DTOAvion notificacion = null;
        Partida partida = recuperarPartida(avionDTO.getNombrePartida());
        if (partida != null) {
            System.out.println(partida.toString());
            Jugador jugador = null;
            Avion avion = null;
            if (avionDTO.getIdJugador() == 1) {
                System.out.println("el jugador 1 movio avion");
                jugador = partida.getJugadorUno();
                avion = partida.getJugadorUno().getListAviones().get(avionDTO.getIdAvion());
                System.out.println("se crea notificacion");

                //se crea la notificacion para dibujar el avion enemigo
                int idAvion = avionDTO.getIdAvion();
                avionDTO.setIdAvion(idAvion);
                notificacion = new DTOAvion(avionDTO);

                //actualizar avion, en usuario de partida
                jugador.getListAviones().remove(avion);
                avion = this.updateAvion(avionDTO);
                jugador.getListAviones().add(avion);
                partida.setJugadorUno(jugador);
            } else {
                jugador = partida.getJugadorDos();
                avion = partida.getJugadorDos().getListAviones().get(avionDTO.getIdAvion());

                //se crea la notificacion para dibujar el avion enemigo
                int idAvion = avionDTO.getIdAvion();
                avionDTO.setIdAvion(idAvion);
                notificacion = new DTOAvion(avionDTO);

                //actualizar avion, en usuario de partida
                jugador.getListAviones().remove(avion);
                avion = this.updateAvion(avionDTO);
                jugador.getListAviones().add(avion);
                partida.setJugadorUno(jugador);
            }
            this.manejadorPartida.updatePartidaEnJuego(partida);
        }
        return notificacion.toString();
    }

    public void moverAvion(DTOAvion avionDTO) {
        String notificacion = null;
        //si el avion se fue de los limites, estalla
        boolean estallarAvion = this.checkAvionFueraLimites(avionDTO);
        if(estallarAvion) {
            avionDTO.setEstado(EstadoAvion.DESTRUIDO);
            //se actualiza la partida y se envia el avion a estallar
            notificacion = this.updateAvionEnPartida(avionDTO);
            this.mensajeriaUpdate.sendEstallarAviones(notificacion.toString());
        } else {
            //se actualiza la partida y se envia el status del avion a el canal aviones-enemigos
            notificacion = this.updateAvionEnPartida(avionDTO);
            this.mensajeriaUpdate.sendAvionesEnemigos(notificacion.toString());
        }
    }
}

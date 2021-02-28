package com.techprimers.springbootwebsocketexample.servicios;

import com.techprimers.springbootwebsocketexample.clases.*;
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
    private final IDAOPartida DAOPartida;

    @Autowired
    public ServicioPartida(ManejadorPartida manejadorPartida, IDAOPartida DAOPartida) {
        this.DAOPartida = DAOPartida;
        this.manejadorPartida = manejadorPartida;
    }

    public int crearPartidaEnEspera(DTOPartidaEnEspera partidaEnEspera) {
        PartidaEnEspera nuevaPartida = new PartidaEnEspera(partidaEnEspera.getNombrePartida(), partidaEnEspera.getNombreJugador());
        this.manejadorPartida.addPartidaEnEspera(nuevaPartida);
        return 1;
    }

    private Partida crearPartida(String nombrePartida, String nombreJugadorUno, String nombreJugadorDos) {
        System.out.println("nombreJugadorDos" + nombreJugadorDos);
        Usuario jugadorUno = new Usuario(nombreJugadorUno);
        jugadorUno.setId(1);
        Usuario jugadorDos = new Usuario(nombreJugadorDos);
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
}

package com.proyecto.bombsaway.servicios;

import com.proyecto.bombsaway.DAOS.IDAOPartida;
import com.proyecto.bombsaway.DTOS.DTOPartidaCompleto;
import com.proyecto.bombsaway.clases.Avion;
import com.proyecto.bombsaway.clases.Partida;
import com.proyecto.bombsaway.clases.Usuario;
import com.proyecto.bombsaway.DTOS.DTOPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioIniciarPartida {
    private final IDAOPartida DAOPartida;

    @Autowired
    public ServicioIniciarPartida(IDAOPartida DAOPartida) {
        this.DAOPartida = DAOPartida;
    }

//    public void iniciarPartida(String nombreJugadorUno, String nombreJugadorDos, String nombrePartida) {
//        try {
//            Usuario jugadorUno = new Usuario(nombreJugadorUno);
//            Usuario jugadorDos = new Usuario(nombreJugadorDos);
//            List<Avion> listAvionesUno = new ArrayList<Avion>();
//            List<Avion> listAvionesDos = new ArrayList<Avion>();
//
//            //inicializacion aviones de cada jugador
//            for( int i = 1; i <= 4; i++) {
//                Avion avionJugadorUno = new Avion(i, 300, 200, 180);
//                listAvionesUno.add(avionJugadorUno);
//                Avion avionJugadorDos = new Avion(i, 800, 200, -180);
//                listAvionesDos.add(avionJugadorDos);
//            }
//            jugadorUno.setListaAviones(listAvionesUno);
//            jugadorDos.setListaAviones(listAvionesDos);
//
//            //creacion nueva partida
//            Partida partida = new Partida(jugadorUno, jugadorDos, nombrePartida);
//            System.out.println(partida.toString());
//
//        }catch (Exception error) {
//            System.out.println("el error" + error);
//        }
//    }

    public int addPartida(DTOPartida partida) {
        System.out.println("la partida que llega");
        System.out.println(partida.toString());
        Usuario jugadorUno = new Usuario(partida.getNombreUsuarioUno());
        Usuario jugadorDos = new Usuario(partida.getNombreUsuarioDos());
        List<Avion> listAvionesUno = new ArrayList<Avion>();
        List<Avion> listAvionesDos = new ArrayList<Avion>();

        //inicializacion aviones de cada jugador
        for( int i = 1; i <= 4; i++) {
            Avion avionJugadorUno = new Avion(i, 300, 200, 180);
            listAvionesUno.add(avionJugadorUno);
            Avion avionJugadorDos = new Avion(i, 800, 200, -180);
            listAvionesDos.add(avionJugadorDos);
        }
        jugadorUno.setListAviones(listAvionesUno);
        jugadorDos.setListAviones(listAvionesDos);

        //creacion nueva partida
        Partida nuevaPartida = new Partida(jugadorUno, jugadorDos, partida.getNombrePartida());
        System.out.println("la partida que se guarda");
        System.out.println(nuevaPartida.toString());
        return this.DAOPartida.insertPartida(nuevaPartida);
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

package com.proyecto.bombsaway.DAOS;

import com.proyecto.bombsaway.clases.Partida;

import java.util.List;

public interface IDAOPartida {

    int insertPartida(String nombrePartida, Partida partida);

    default int insertPartida(Partida partida){
        return insertPartida(partida.getNombre(), partida);
    }

    List<Partida> getListPartidas();
}

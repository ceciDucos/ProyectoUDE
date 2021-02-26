package com.techprimers.springbootwebsocketexample.daos;

import com.techprimers.springbootwebsocketexample.clases.Partida;

import java.util.List;

public interface IDAOPartida {

    int insertPartida(String nombrePartida, Partida partida);

    default int insertPartida(Partida partida){
        return insertPartida(partida.getNombre(), partida);
    }

    List<Partida> getListPartidas();
}

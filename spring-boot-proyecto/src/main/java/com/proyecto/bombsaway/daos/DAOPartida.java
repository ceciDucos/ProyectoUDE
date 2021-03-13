package com.proyecto.bombsaway.daos;

import com.proyecto.bombsaway.clases.Partida;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DAOPartida implements IDAOPartida{
    private static List<Partida> DB = new ArrayList<>();

    @Override
    public int insertPartida(String nombrePartida, Partida partida) {
        DB.add(new Partida(partida.getJugadorUno(), partida.getJugadorDos(), partida.getNombre()));
        return 1;
    }

    @Override
    public List<Partida> getListPartidas() {
        return this.DB;
    }
}

package com.techprimers.springbootwebsocketexample.daos;

import com.techprimers.springbootwebsocketexample.clases.Partida;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DAOPartida implements IDAOPartida{
    private static List<Partida> DB = new ArrayList<>();

    @Override
    public int insertPartida(String nombrePartida, Partida partida) {
        System.out.println("la Partida que se guarda: ");
        System.out.println(partida.toString());
        DB.add(new Partida(partida.getJugadorUno(), partida.getJugadorDos(), partida.getNombre()));
        return 1;
    }

    @Override
    public List<Partida> getListPartidas() {
        return this.DB;
    }
}

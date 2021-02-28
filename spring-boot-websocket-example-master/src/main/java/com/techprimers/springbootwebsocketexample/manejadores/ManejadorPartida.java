package com.techprimers.springbootwebsocketexample.manejadores;

import com.techprimers.springbootwebsocketexample.clases.Partida;
import com.techprimers.springbootwebsocketexample.clases.PartidaEnEspera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ManejadorPartida {
    private List<PartidaEnEspera> partidasEnEspera = new ArrayList<>();
    private List<Partida> partidasEnJuego = new ArrayList<>();

    @Autowired
    public ManejadorPartida() { }

    public ManejadorPartida(List<PartidaEnEspera> partidasEnEspera, List<Partida> partidasEnJuego) {
        this.partidasEnEspera = partidasEnEspera;
        this.partidasEnJuego = partidasEnJuego;
    }

    public List<PartidaEnEspera> getPartidasEnEspera() {
        return partidasEnEspera;
    }

    public List<Partida> getPartidasEnJuego() {
        return partidasEnJuego;
    }

    //Se inicia una nueva partida por un jugador
    public void addPartidaEnEspera(PartidaEnEspera nuevaPartida) {
        this.partidasEnEspera.add(nuevaPartida);
    }

    //un jugador se ha unido a una partida iniciada
    public void addPartidaEnJuego(Partida partidaEnJuego) {
        //se elimina la partida de la espera y se agrega a partidas en juego
        PartidaEnEspera partidaParaRemover = null;
        for (PartidaEnEspera partidaEnEspera: partidasEnEspera) {
            if (partidaEnEspera.getNombrePartida() == partidaEnJuego.getNombre()) {
               partidaParaRemover = partidaEnEspera;
            }
        }
        if(partidaParaRemover != null) {
            this.partidasEnEspera.remove(partidaParaRemover);
            this.partidasEnJuego.add(partidaEnJuego);
        }
    }
}

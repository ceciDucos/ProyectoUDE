package com.proyecto.bombsaway.manejadores;

import com.proyecto.bombsaway.clases.Partida;
import com.proyecto.bombsaway.clases.PartidaEnEspera;
import com.proyecto.bombsaway.excepciones.ConcurrenciaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ManejadorPartida {
    private List<PartidaEnEspera> partidasEnEspera = new ArrayList<>();
    private List<Partida> partidasEnJuego = new ArrayList<>();
    private int jugadoresConectados = 0;

    @Scope("singleton")
    public static ManejadorPartida getManejadorPartida() {
        return new ManejadorPartida();
    }

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
    public synchronized void addPartidaEnEspera(PartidaEnEspera nuevaPartida) throws ConcurrenciaException {
        try {
            while(this.jugadoresConectados > 0) {
                wait();
            }
            this.jugadoresConectados++;
            this.partidasEnEspera.add(nuevaPartida);
            this.jugadoresConectados--;
            notify();
        } catch (InterruptedException error) {
            throw new ConcurrenciaException("Error de concurrencia crear partida en espera");
        } catch (Exception error) {
            throw new ConcurrenciaException("Error en Manejador Partida al crear partida en espera");
        }
    }

    //un jugador se ha unido a una partida iniciada
    public synchronized void addPartidaEnJuego(Partida partidaEnJuego) throws ConcurrenciaException {
        try {
            System.out.println("test 0");
            while(this.jugadoresConectados > 0) {
                wait();
            }
            System.out.println("test 1");
            this.jugadoresConectados++;
            System.out.println("test 2");
            //se elimina la partida de la espera y se agrega a partidas en juego
            PartidaEnEspera partidaParaRemover = null;
            System.out.println("test 3");
            for (PartidaEnEspera partidaEnEspera: partidasEnEspera) {
                System.out.println("test partida: " + partidaEnEspera);
                if (partidaEnEspera.getNombrePartida().equalsIgnoreCase(partidaEnJuego.getNombre())) {
                    System.out.println("test 4");
                    partidaParaRemover = partidaEnEspera;
                }
            }
            System.out.println("test 5");
            if(partidaParaRemover != null) {
                System.out.println("test 6");
                this.partidasEnEspera.remove(partidaParaRemover);
                System.out.println("test 7");
                this.partidasEnJuego.add(partidaEnJuego);
                System.out.println("test 8");
            }
            System.out.println("test 9");
            this.jugadoresConectados--;
            System.out.println("test 10");
            notify();
            System.out.println("test 11");
        } catch (InterruptedException error) {
            throw new ConcurrenciaException("Error de concurrencia al pasar partida de espera a en juego");
        } catch (Exception error) {
            throw new ConcurrenciaException("Error en Manejador Partida al pasar partida de espera a en juego");
        }
    }

    public synchronized Partida getPartidaEnJuego(String nombrePartida) throws ConcurrenciaException {
        Partida res = null;
        try {
            while(this.jugadoresConectados > 0) {
                wait();
            }
            this.jugadoresConectados++;
            for (Partida partidaEnJuego: partidasEnJuego) {
                if (partidaEnJuego.getNombre().equalsIgnoreCase(nombrePartida)) {
                    res = partidaEnJuego;
                }
            }
            this.jugadoresConectados--;
            notify();
        } catch (InterruptedException error) {
            throw new ConcurrenciaException("Error de concurrencia al obtener partida en juego");
        } catch (Exception error) {
            throw new ConcurrenciaException("Error en Manejador Partida al obtener partida en juego");
        }
        return res;
    }

    public synchronized void updatePartidaEnJuego(Partida partidaActual) throws ConcurrenciaException {
        try {
            while(this.jugadoresConectados > 0) {
                wait();
            }
            this.jugadoresConectados++;
            Partida partidaAnterior = null;
            for (Partida partidaEnJuego: partidasEnJuego) {
                if (partidaEnJuego.getNombre().equalsIgnoreCase(partidaActual.getNombre())) {
                    partidaAnterior = partidaEnJuego;
                }
            }
            if(partidaAnterior != null) {
                partidaAnterior.setJugadorUno(partidaActual.getJugadorUno());
                partidaAnterior.setJugadorDos(partidaActual.getJugadorDos());
            }
            this.jugadoresConectados--;
            notify();
        } catch (InterruptedException error) {
            throw new ConcurrenciaException("Error de concurrencia al actualizar partida");
        } catch (Exception error) {
            throw new ConcurrenciaException("Error en Manejador Partida al actualizar partida");
        }
    }
}

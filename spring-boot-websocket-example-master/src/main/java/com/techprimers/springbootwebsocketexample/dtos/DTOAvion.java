package com.techprimers.springbootwebsocketexample.dtos;

import com.techprimers.springbootwebsocketexample.clases.EstadoAvion;

public class DTOAvion {
    private String nombrePartida;
    private int idJugador;
    private int idAvion;
    private int ejeX;
    private int ejeY;
    private int angulo;
    private EstadoAvion estado;
    private int vida;
    private int combustible;
    private boolean tieneBomba;

    public DTOAvion(){};

    public DTOAvion(int id, int ejeX,
                    int ejeY, int angulo, EstadoAvion estado, int vida, int combustible,
                    boolean tieneBomba) {
        this.idAvion = id;
        this.ejeX = ejeX;
        this.ejeY = ejeY;
        this.angulo = angulo;
        this.estado = estado;
        this.vida = vida;
        this.combustible = combustible;
        this.tieneBomba = tieneBomba;
    }

    public int getEjeX() {
        return ejeX;
    }

    public int getEjeY() {
        return ejeY;
    }

    public int getAngulo() {
        return angulo;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public int getIdAvion() {
        return idAvion;
    }

    @Override
    public String toString() {
        String res = "Avion: [" + this.idAvion + ", " + this.ejeX + ", " +
                this.ejeY + ", " + this.angulo + " ]";
        return res;
    }
}

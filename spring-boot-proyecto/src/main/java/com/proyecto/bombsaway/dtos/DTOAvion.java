package com.proyecto.bombsaway.dtos;

import com.proyecto.bombsaway.enumerados.EstadoAvion;

import javax.json.Json;
import javax.json.JsonObject;

public class DTOAvion {
    private String nombrePartida;
    private int idJugador;
    private int idAvion;
    private double ejeX;
    private double ejeY;
    private double angulo;
    private EstadoAvion estado;
    private int vida;
    private int combustible;
    private boolean tieneBomba;
    private boolean visible;

    public DTOAvion(){};

    public DTOAvion(int id, double ejeX,
                    double ejeY, double angulo, EstadoAvion estado, int vida, int combustible,
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

    public DTOAvion(DTOAvion dtoAvion) {
        this.nombrePartida = dtoAvion.getNombrePartida();
        this.idJugador = dtoAvion.getIdJugador();
        this.idAvion = dtoAvion.getIdAvion();
        this.ejeX = dtoAvion.getEjeX();
        this.ejeY = dtoAvion.getEjeY();
        this.angulo = dtoAvion.getAngulo();
        this.estado = dtoAvion.getEstado();
        this.vida = dtoAvion.getVida();
        this.combustible = dtoAvion.getCombustible();
        this.tieneBomba = dtoAvion.isTieneBomba();
        this.visible = dtoAvion.isVisible();
    }
    public void setNombrePartida(String nombrePartida) {
        this.nombrePartida = nombrePartida;
    }

    public void setEjeX(double ejeX) {
        this.ejeX = ejeX;
    }

    public void setEjeY(double ejeY) {
        this.ejeY = ejeY;
    }

    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }

    public void setEstado(EstadoAvion estado) {
        this.estado = estado;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setCombustible(int combustible) {
        this.combustible = combustible;
    }

    public void setTieneBomba(boolean tieneBomba) {
        this.tieneBomba = tieneBomba;
    }

    public double getEjeX() {
        return ejeX;
    }

    public double getEjeY() {
        return ejeY;
    }

    public double getAngulo() {
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

    public String getNombrePartida() {
        return nombrePartida;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public EstadoAvion getEstado() {
        return estado;
    }

    public int getVida() {
        return vida;
    }

    public int getCombustible() {
        return combustible;
    }

    public boolean isTieneBomba() {
        return tieneBomba;
    }

    @Override
    public String toString() {
        String res = "{\"nombrePartida\":\"" + this.nombrePartida + "\",\"idAvion\":" + this.idAvion +
                ",\"idJugador\":" + this.idJugador + ",\"ejeX\":" + this.ejeX + ",\"ejeY\":" + this.ejeY +
                ",\"angulo\":" + this.angulo + ",\"estado\":" + EstadoAvion.valueOf(this.estado.toString()).ordinal() +
                ",\"vida\":" + this.vida +
                ",\"combustible\":" + this.combustible + ",\"tieneBomba\":" + this.tieneBomba + ",\"visible\":" +
                this.visible + "}";
        return res;
    }
}

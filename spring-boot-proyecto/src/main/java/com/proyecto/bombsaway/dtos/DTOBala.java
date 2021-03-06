package com.proyecto.bombsaway.dtos;

import com.proyecto.bombsaway.enumerados.EstadoAvion;

public class DTOBala {
    private String nombrePartida;
    private int idJugador ;
    private int idElemento;
    private int idBala;
    private EstadoAvion altitud;
    private double ejeX;
    private double ejeY;
    private double angulo;
    private boolean visible;

    public DTOBala(){}

    public String getNombrePartida() {
        return nombrePartida;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public int getIdElemento() {
        return idElemento;
    }

    public int getIdBala() {
        return idBala;
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

    public void setNombrePartida(String nombrePartida) {
        this.nombrePartida = nombrePartida;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public void setIdElemento(int idElemento) {
        this.idElemento = idElemento;
    }

    public void setIdBala(int idBala) {
        this.idBala = idBala;
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

    public void setAltitud(EstadoAvion altitud) {
        this.altitud = altitud;
    }

    public EstadoAvion getAltitud() {
        return altitud;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        String res = "{\"nombrePartida\":\"" + this.nombrePartida + "\",\"idJugador\":" + this.idJugador +
                ",\"idElemento\":" + this.idElemento + ",\"ejeX\":" + this.ejeX + ",\"ejeY\":" + this.ejeY +
                ",\"angulo\":" + this.angulo + ",\"altitud\":" + this.altitud.getValueString() +
                ",\"visible\":" + this.visible + ",\"idBala\":" + this.idBala + "}";
        return res;
    }
}

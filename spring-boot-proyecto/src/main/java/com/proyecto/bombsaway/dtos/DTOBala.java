package com.proyecto.bombsaway.dtos;

import com.proyecto.bombsaway.enumerados.EstadoAvion;

public class DTOBala {
    private String nombrePartida;
    private int idJugador ;
    private int idAvion;
    private int idBala;
    private EstadoAvion altitud;
    private int ejeX;
    private int ejeY;
    private int angulo;
    private boolean visible;

    public DTOBala(){}

    public String getNombrePartida() {
        return nombrePartida;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public int getIdAvion() {
        return idAvion;
    }

    public int getIdBala() {
        return idBala;
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

    public void setNombrePartida(String nombrePartida) {
        this.nombrePartida = nombrePartida;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public void setIdBala(int idBala) {
        this.idBala = idBala;
    }

    public void setEjeX(int ejeX) {
        this.ejeX = ejeX;
    }

    public void setEjeY(int ejeY) {
        this.ejeY = ejeY;
    }

    public void setAngulo(int angulo) {
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
//        String res = "{\"nombrePartida\":\"" + this.nombrePartida + "\",\"idJugador\":" + this.idJugador +
//                ",\"idAvion\":" + this.idAvion + ",\"ejeX\":" + this.ejeX + ",\"ejeY\":" + this.ejeY +
//                ",\"angulo\":" + this.angulo + ",\"altitud\":" + String.valueOf(this.altitud.ordinal()) +
//                ",\"visible\":" + this.visible + ",\"idBala\":" + this.idBala + "}";
//        return res;
        String res = "{\"nombrePartida\":\"" + this.nombrePartida + "\",\"idJugador\":" + this.idJugador +
                ",\"idAvion\":" + this.idAvion + ",\"ejeX\":" + this.ejeX + ",\"ejeY\":" + this.ejeY +
                ",\"angulo\":" + this.angulo + ",\"altitud\":" + 1 +
                ",\"visible\":" + this.visible + ",\"idBala\":" + this.idBala + "}";
        return res;
    }
}

package com.proyecto.bombsaway.dtos;

import java.util.List;

public class DTOVisibilidad {
    private String nombrePartida;
    private int idJugador ;
    private boolean visibilidadBase;
    private List<Boolean> visibilidadAviones;
    private List<Boolean> visibilidadArtilleria;
    
    public DTOVisibilidad() {}

    public String getNombrePartida() {
        return nombrePartida;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public boolean isVisibilidadBase() {
        return visibilidadBase;
    }

    public List<Boolean> getVisibilidadAviones() {
        return visibilidadAviones;
    }

    public List<Boolean> getVisibilidadArtilleria() {
        return visibilidadArtilleria;
    }

    public void setNombrePartida(String nombrePartida) {
        this.nombrePartida = nombrePartida;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public void setVisibilidadBase(boolean visibilidadBase) {
        this.visibilidadBase = visibilidadBase;
    }

    public void setVisibilidadAviones(List<Boolean> visibilidadAviones) {
        this.visibilidadAviones = visibilidadAviones;
    }

    public void setVisibilidadArtilleria(List<Boolean> visibilidadArtilleria) {
        this.visibilidadArtilleria = visibilidadArtilleria;
    }

    @Override
    public String toString() {
        String res = "{\"nombrePartida\":\"" + this.nombrePartida + "\",\"idJugador\":" + this.idJugador +
                ",\"visibilidadBase\":" + this.visibilidadBase +
                ",\"visibilidadAviones\": [ ";
        int i = 0;
        while (i < this.visibilidadAviones.size() ) {
            res += this.visibilidadAviones.get(i);
            i++;
            if(i < this.visibilidadAviones.size()) {
                res += ",";
            }
        }
        res+= "], ";
        res+= "\"visibilidadArtilleria\": [ ";
        i = 0;
        while (i < this.visibilidadArtilleria.size() ) {
            res += this.visibilidadArtilleria.get(i);
            i++;
            if(i < this.visibilidadArtilleria.size()) {
                res += ",";
            }
        }
        res+= "] }";
        return res;
    }
}

package com.proyecto.bombsaway.dtos;

public class DTOMensaje {
    private String accion;
    private String nombrePartida;

    public String getNombrePartida() {
        return nombrePartida;
    }

    public void setNombrePartida(String nombrePartida) {
        this.nombrePartida = nombrePartida;
    }

    public DTOMensaje(){}

    public DTOMensaje(String accion) {
        this.accion = accion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    @Override
    public String toString() {
        String res = "{\"accion\":\"" + this.accion + "\",\"nombrePartida\":\"" + this.nombrePartida + "\"}";
        return res;
    }
}

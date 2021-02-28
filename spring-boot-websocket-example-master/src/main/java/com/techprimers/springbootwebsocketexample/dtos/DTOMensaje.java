package com.techprimers.springbootwebsocketexample.dtos;

public class DTOMensaje {
    private String accion;

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
}

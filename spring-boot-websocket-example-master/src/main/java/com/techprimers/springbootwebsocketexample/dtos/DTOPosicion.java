package com.techprimers.springbootwebsocketexample.dtos;

public class DTOPosicion {
    private int ejeX;
    private int ejeY;
    private int angulo;

    public DTOPosicion(int ejeX, int ejeY, int angulo) {
        this.ejeX = ejeX;
        this.ejeY = ejeY;
        this.angulo = angulo;
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
}
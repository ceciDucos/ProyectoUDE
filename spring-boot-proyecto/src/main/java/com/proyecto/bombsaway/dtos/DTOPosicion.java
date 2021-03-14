package com.proyecto.bombsaway.dtos;

public class DTOPosicion {
    private double ejeX;
    private double ejeY;
    private double angulo;

    public DTOPosicion(double ejeX, double ejeY, double angulo) {
        this.ejeX = ejeX;
        this.ejeY = ejeY;
        this.angulo = angulo;
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
}
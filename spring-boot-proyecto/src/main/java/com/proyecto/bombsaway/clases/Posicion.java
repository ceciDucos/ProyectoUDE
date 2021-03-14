package com.proyecto.bombsaway.clases;

public class Posicion {
    private double ejeX;
    private double ejeY;
    private double angulo;

    public Posicion(double ejeX, double ejeY, double angulo) {
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

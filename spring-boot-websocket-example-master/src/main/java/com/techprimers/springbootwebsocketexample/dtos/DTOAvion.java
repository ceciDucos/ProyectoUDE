package com.techprimers.springbootwebsocketexample.dtos;

public class DTOAvion {
    private int id;
    private int ejeX;
    private int ejeY;
    private int angulo;

    public DTOAvion(){};

    public DTOAvion(int id, int ejeX, int ejeY, int angulo) {
        this.id = id;
        this.ejeX = ejeX;
        this.ejeY = ejeY;
        this.angulo = angulo;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        String res = "Avion: [" + this.id + ", " + this.ejeX + ", " +
                this.ejeY + ", " + this.angulo + " ]";
        return res;
    }
}

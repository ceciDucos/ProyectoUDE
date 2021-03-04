package com.proyecto.bombsaway.dtos;

public class DTOAvionTest {
    String ejeX;
    String ejeY;
    String angulo;
    String accion;
    String idAvion;
    String nombreJugador;

    public DTOAvionTest(){};

    public DTOAvionTest(String ejeX, String ejeY, String angulo) {
        this.ejeX = ejeX;
        this.ejeY = ejeY;
        this.angulo = angulo;
    }

    public DTOAvionTest(String ejeX, String ejeY, String angulo, String accion, String idAvion, String nombreJugador) {
        this.ejeX = ejeX;
        this.ejeY = ejeY;
        this.angulo = angulo;
        this.accion = accion;
        this.idAvion = idAvion;
        this.nombreJugador = nombreJugador;
    }

    public String getEjeX() {
        return ejeX;
    }

    public String getEjeY() {
        return ejeY;
    }

    public String getAngulo() {
        return angulo;
    }

    public void setEjeX(String ejeX) {
        this.ejeX = ejeX;
    }

    public void setEjeY(String ejeY) {
        this.ejeY = ejeY;
    }

    public void setAngulo(String angulo) {
        this.angulo = angulo;
    }
}

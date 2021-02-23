package com.proyecto.bombsaway.clases;

import com.proyecto.bombsaway.DTOS.DTOAvion;
import com.proyecto.bombsaway.DTOS.DTOPosicion;

public class Avion {
    private Posicion posicion;
    private final int id;

    public Avion(int id, int ejeX, int ejeY, int angulo) {
        this.posicion = new Posicion(ejeX, ejeY, angulo);
        this.id = id;
    }

    public DTOAvion getDTO() {
        DTOPosicion DTOPos = new DTOPosicion(this.posicion.getEjeX(), this.posicion.getEjeY(),
                this.posicion.getAngulo());
        DTOAvion avion = new DTOAvion(this.id, DTOPos);
        return avion;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public int getId() {
        return id;
    }

}

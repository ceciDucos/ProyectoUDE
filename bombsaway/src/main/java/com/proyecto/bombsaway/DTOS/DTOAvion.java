package com.proyecto.bombsaway.DTOS;

public class DTOAvion {
    private int id;
    private DTOPosicion posicion;

    public DTOAvion(int idAvion, DTOPosicion posicion) {
        this.id = idAvion;
        this.posicion = posicion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosicion(DTOPosicion posicion) {
        this.posicion = posicion;
    }

    public int getId() {
        return id;
    }
}

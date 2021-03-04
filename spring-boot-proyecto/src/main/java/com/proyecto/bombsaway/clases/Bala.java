package com.proyecto.bombsaway.clases;

public class Bala {
    private Posicion posicion;
    private int id;
    private boolean visible;

    public Bala(Posicion posicion, int id) {
        this.posicion = posicion;
        this.id = id;
    }

    public Bala() { }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public int getId() {
        return id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

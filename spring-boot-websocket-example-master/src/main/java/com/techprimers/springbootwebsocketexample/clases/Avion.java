package com.techprimers.springbootwebsocketexample.clases;

import com.techprimers.springbootwebsocketexample.dtos.DTOAvion;
import com.techprimers.springbootwebsocketexample.dtos.DTOPosicion;

import javax.json.Json;
import javax.json.JsonObject;

public class Avion {
    private int id;
    private Posicion posicion;
    private EstadoAvion estado;
    private int vida;
    private int combustible;
    private boolean tieneBomba;

    public Avion(){};

    public Avion(int id, Posicion posicion) {
        this.id = id;
        this.posicion = posicion;
    }

    public DTOAvion getDTO() {
        DTOPosicion DTOPos = new DTOPosicion(this.posicion.getEjeX(), this.posicion.getEjeY(),
                this.posicion.getAngulo());
        DTOAvion avion = new DTOAvion(this.id, this.posicion.getEjeX(), this.posicion.getEjeY(),
                this.posicion.getAngulo(), this.estado, this.vida, this.combustible, this.tieneBomba);
        return avion;
    }

    public int getId() {
        return id;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("avion:", Json.createObjectBuilder()
                        .add("id", this.id)
                        .add("ejeX", this.getPosicion().getEjeX())
                        .add("ejeY", this.getPosicion().getEjeY())
                        .add("angulo", this.getPosicion().getAngulo())
                        .add("estado", String.valueOf(this.estado))
                        .add("vida", this.vida)
                        .add("combustible", this.combustible)
                        .add("tieneBomba", this.tieneBomba)
                ).build();
    }
}

package com.proyecto.bombsaway.clases;

import com.proyecto.bombsaway.dtos.DTOAvion;
import com.proyecto.bombsaway.dtos.DTOPosicion;
import com.proyecto.bombsaway.enumerados.EstadoAvion;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class Avion {
    private int id;
    private Posicion posicion;
    private EstadoAvion estado;
    private int vida;
    private int combustible;
    private boolean tieneBomba;
    private List<Bala> listaBalas;

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

    public void inicializarBalas() {
        List<Bala> listaBalasAvion = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            Bala balaAvion = new Bala();
            balaAvion.setId(i);
            balaAvion.setVisible(false);
            listaBalasAvion.add(balaAvion);
        }
        this.setListaBalas(listaBalasAvion);
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

    public void updateBala(Bala bala) {
        Bala actual = this.listaBalas.get(bala.getId());
        actual.setVisible(bala.isVisible());
        actual.setPosicion(bala.getPosicion());
    }

    public void setEstado(EstadoAvion estado) {
        this.estado = estado;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setCombustible(int combustible) {
        this.combustible = combustible;
    }

    public void setTieneBomba(boolean tieneBomba) {
        this.tieneBomba = tieneBomba;
    }

    public EstadoAvion getEstado() {
        return estado;
    }

    public int getVida() {
        return vida;
    }

    public int getCombustible() {
        return combustible;
    }

    public boolean isTieneBomba() {
        return tieneBomba;
    }

    public List<Bala> getListaBalas() {
        return listaBalas;
    }

    public void setListaBalas(List<Bala> listaBalas) {
        this.listaBalas = listaBalas;
    }
}

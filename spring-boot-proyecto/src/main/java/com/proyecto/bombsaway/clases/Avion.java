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
    private List<Bala> listBalas;
    private boolean visible;

    public Avion(){};

    public Avion(int id, Posicion posicion) {
        this.id = id;
        this.posicion = posicion;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public DTOAvion getDTO() {
        DTOPosicion DTOPos = new DTOPosicion(this.posicion.getEjeX(), this.posicion.getEjeY(),
                this.posicion.getAngulo());
        DTOAvion avion = new DTOAvion(this.id, this.posicion.getEjeX(), this.posicion.getEjeY(),
                this.posicion.getAngulo(), this.estado, this.vida, this.combustible, this.tieneBomba);
        return avion;
    }

    public void updateAvion(DTOAvion dtoAvion) {
        this.setId(dtoAvion.getIdAvion());
        this.setPosicion(new Posicion(dtoAvion.getEjeX(), dtoAvion.getEjeY(), dtoAvion.getAngulo()));
        //this.setVida(dtoAvion.getVida());
        this.setCombustible(dtoAvion.getCombustible());
        this.setEstado(dtoAvion.getEstado());
        this.setTieneBomba(dtoAvion.isTieneBomba());
    }

    public void inicializarBalas() {
        this.listBalas = new ArrayList<Bala>();
        for (int i = 0; i < 30; i++){
            Bala balaAvion = new Bala();
            balaAvion.setId(i);
            balaAvion.setVisible(false);
            listBalas.add(balaAvion);
        }
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

    public void updateBala(Bala bala) {
        Bala actual = this.listBalas.get(bala.getId());
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

    public List<Bala> getListBalas() {
        return listBalas;
    }

    public void setListaBalas(List<Bala> listaBalas) {
        this.listBalas = listBalas;
    }

    public boolean isVisible() {
        return visible;
    }
}

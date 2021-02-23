package com.proyecto.bombsaway.clases;

import com.proyecto.bombsaway.DTOS.DTOAvion;
import com.proyecto.bombsaway.DTOS.DTOUsuario;

import java.util.ArrayList;
import java.util.List;


public class Usuario {
    private String nombre;
    private List<Avion> listAviones;

    public Usuario(String nomUsuario) {
        this.nombre = nomUsuario;
        this.listAviones = new ArrayList<Avion>();
    }

    public DTOUsuario getDTO() {
        DTOUsuario res = new DTOUsuario(this.nombre);
        List<DTOAvion> resListAviones = new ArrayList<DTOAvion>();
        for (Avion avion: this.listAviones) {
            DTOAvion avionDTO = avion.getDTO();
            resListAviones.add(avionDTO);
        }
        res.setListAviones(resListAviones);
        return res;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setListAviones(List<Avion> listAviones) {
        this.listAviones = listAviones;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Avion> getListAviones() {
        return listAviones;
    }
}

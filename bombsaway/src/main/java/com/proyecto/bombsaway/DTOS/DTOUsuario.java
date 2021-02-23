package com.proyecto.bombsaway.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DTOUsuario {
    private String nombre;
    private List<DTOAvion> listAviones;


    public DTOUsuario(@JsonProperty("nombre") String nomUsuario) {
        this.nombre = nomUsuario;
        this.listAviones = new ArrayList<DTOAvion>();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setListAviones(List<DTOAvion> listaAviones) {
        this.listAviones = listaAviones;
    }

    public String getNombre() {
        return nombre;
    }

    public List<DTOAvion> getListAviones() {
        return listAviones;
    }
}

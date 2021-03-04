package com.proyecto.bombsaway.dtos;

import java.util.ArrayList;
import java.util.List;

public class DTOUsuario {
    private String nombreJugador;
    private List<DTOAvion> listAviones;

    public DTOUsuario() {}

    public DTOUsuario(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.listAviones = new ArrayList<DTOAvion>();
    }

    public void setNombre(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public void setListAviones(List<DTOAvion> listaAviones) {
        this.listAviones = listaAviones;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public List<DTOAvion> getListAviones() {
        return listAviones;
    }
}

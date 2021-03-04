package com.proyecto.bombsaway.clases;

import com.proyecto.bombsaway.dtos.DTOPartidaCompleto;
import com.proyecto.bombsaway.dtos.DTOUsuario;
import org.springframework.beans.factory.annotation.Autowired;

public class Partida {
    private String nombre;
    private Jugador jugadorUno;
    private Jugador jugadorDos;

    @Autowired
    public Partida(Jugador jugadorUno, Jugador jugadorDos, String nombrePartida) {
        this.nombre = nombrePartida;
        this.jugadorUno = jugadorUno;
        this.jugadorDos = jugadorDos;
    }

    public String getNombre() {
        return nombre;
    }

    public Jugador getJugadorUno() {
        return jugadorUno;
    }

    public Jugador getJugadorDos() {
        return jugadorDos;
    }

    public DTOPartidaCompleto getDTOCompleto() {
        DTOPartidaCompleto res = new DTOPartidaCompleto(this.jugadorUno.getNombre(), this.jugadorDos.getNombre(), this.nombre);
        DTOUsuario usuarioUno =  this.jugadorUno.getDTO();
        DTOUsuario usuarioDos =  this.jugadorDos.getDTO();
        res.setUsuarioUno(usuarioUno);
        res.setUsuarioDos(usuarioDos);
        return res;
    }

    @Override
    public String toString() {
        String res = "Partida: [" + this.getNombre() + ", " + this.jugadorUno.toString() + ", " +
                this.jugadorDos.toString() + " ]";
        return res;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setJugadorUno(Jugador jugadorUno) {
        this.jugadorUno = jugadorUno;
    }

    public void setJugadorDos(Jugador jugadorDos) {
        this.jugadorDos = jugadorDos;
    }
}

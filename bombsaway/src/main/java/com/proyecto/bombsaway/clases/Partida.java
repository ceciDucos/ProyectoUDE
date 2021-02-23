package com.proyecto.bombsaway.clases;

import com.proyecto.bombsaway.DTOS.DTOPartidaCompleto;
import com.proyecto.bombsaway.DTOS.DTOUsuario;
import org.springframework.beans.factory.annotation.Autowired;

public class Partida {
    private String nombre;
    private Usuario usuarioUno;
    private Usuario usuarioDos;

    @Autowired
    public Partida(Usuario usuarioUno, Usuario usuarioDos, String nombrePartida) {
        this.nombre = nombrePartida;
        this.usuarioUno = usuarioUno;
        this.usuarioDos = usuarioDos;
    }

    public String getNombre() {
        return nombre;
    }

    public Usuario getJugadorUno() {
        return usuarioUno;
    }

    public Usuario getJugadorDos() {
        return usuarioDos;
    }

    public DTOPartidaCompleto getDTOCompleto() {
        DTOPartidaCompleto res = new DTOPartidaCompleto(this.usuarioUno.getNombre(), this.usuarioDos.getNombre(), this.nombre);
        DTOUsuario usuarioUno =  this.usuarioUno.getDTO();
        DTOUsuario usuarioDos =  this.usuarioDos.getDTO();
        res.setUsuarioUno(usuarioUno);
        res.setUsuarioDos(usuarioDos);
        return res;
    }

    @Override
    public String toString() {
        String res = "Partida: [" + this.getNombre() + ", " + this.usuarioUno.getNombre() + ", " +
                this.usuarioDos.getNombre() + " ]";
        return res;
    }
}

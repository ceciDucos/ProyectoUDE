package com.techprimers.springbootwebsocketexample.dtos;

public class DTOPartidaCompleto extends DTOPartida{
    private DTOUsuario usuarioUno;
    private DTOUsuario usuarioDos;

    public DTOPartidaCompleto(String nombreUsuarioUno, String nombreUsuarioDos, String nombrePartida) {
        super(nombreUsuarioUno, nombreUsuarioDos, nombrePartida);
    }

    public void setUsuarioUno(DTOUsuario usuarioUno) {
        this.usuarioUno = usuarioUno;
    }

    public void setUsuarioDos(DTOUsuario usuarioDos) {
        this.usuarioDos = usuarioDos;
    }

    @Override
    public String toString() {
        String res = "DTOPartidaCompleto: [" + this.getNombrePartida() + ", " + this.usuarioUno.getNombreJugador() + ", " +
                this.usuarioDos.getNombreJugador() + " ]";
        return res;
    }
}

package com.proyecto.bombsaway.excepciones;

public class ConcurrenciaException extends Exception {
    String mensaje = "";

    public ConcurrenciaException(String mensajeError) {
        this.mensaje = mensajeError;
    }

    public String getMensaje() {
        return mensaje;
    }
}

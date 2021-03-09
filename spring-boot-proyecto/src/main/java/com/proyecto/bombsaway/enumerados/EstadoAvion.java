package com.proyecto.bombsaway.enumerados;

public enum EstadoAvion {
    QUIETO("0"),
    ALTURA_BAJA("1"),
    ALTURA_ALTA("2"),
    DESTRUIDO("3");

    private final String valueString;

    EstadoAvion(String value) {
        this.valueString = value;
    }

    public String getValueString() { return valueString; }
}

package com.proyecto.bombsaway.dtos;

import com.proyecto.bombsaway.enumerados.EstadoAvion;

import java.util.List;

public class DTOAvionBala extends DTOAvion {
    private List<DTOBala> listBalas;

    public DTOAvionBala() { }

    public DTOAvionBala(int id, int ejeX, int ejeY, int angulo, EstadoAvion estado, int vida, int combustible, boolean tieneBomba) {
        super(id, ejeX, ejeY, angulo, estado, vida, combustible, tieneBomba);
    }

    public DTOAvionBala(DTOAvion dtoAvion) {
        super(dtoAvion);
    }
}

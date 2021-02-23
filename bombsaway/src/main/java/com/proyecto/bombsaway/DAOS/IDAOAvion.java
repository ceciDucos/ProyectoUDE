package com.proyecto.bombsaway.DAOS;

import com.proyecto.bombsaway.clases.Avion;

import java.util.List;

public interface IDAOAvion {

    int insertAvion(int id, Avion avion);

    default int insertAvion(Avion avion){
        return insertAvion(avion.getId(), avion);
    }

    List<Avion> getListAviones();
}

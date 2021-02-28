package com.techprimers.springbootwebsocketexample.daos;

import com.techprimers.springbootwebsocketexample.clases.Avion;

import java.util.List;

public interface IDAOAvion {

    int insertAvion(int id, Avion avion);

    default int insertAvion(Avion avion){
//        return insertAvion(avion.getId(), avion);
        return 1; //borrar este
    }

    List<Avion> getListAviones();
}

package com.proyecto.bombsaway.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.bombsaway.entidades.EntidadPartida;

public interface IDAOPartida extends JpaRepository<EntidadPartida, Integer> {

}

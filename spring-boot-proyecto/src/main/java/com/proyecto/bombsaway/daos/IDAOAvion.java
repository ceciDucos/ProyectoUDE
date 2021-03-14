package com.proyecto.bombsaway.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.bombsaway.entidades.EntidadAvion;

public interface IDAOAvion extends JpaRepository<EntidadAvion, Integer> {

}

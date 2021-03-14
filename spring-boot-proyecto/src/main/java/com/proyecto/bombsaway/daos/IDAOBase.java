package com.proyecto.bombsaway.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.bombsaway.entidades.EntidadBase;

public interface IDAOBase extends JpaRepository<EntidadBase, Integer> {

}

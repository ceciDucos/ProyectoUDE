package com.proyecto.bombsaway.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.bombsaway.entidades.EntidadElementoBase;

public interface IDAOElementoBase extends JpaRepository<EntidadElementoBase, Integer> {

}

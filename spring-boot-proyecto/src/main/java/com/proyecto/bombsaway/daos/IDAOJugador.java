package com.proyecto.bombsaway.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.bombsaway.entidades.EntidadJugador;

public interface IDAOJugador extends JpaRepository<EntidadJugador, Integer> {

	public boolean existsByNumeroAndNombre(int numero, String nombre);

}

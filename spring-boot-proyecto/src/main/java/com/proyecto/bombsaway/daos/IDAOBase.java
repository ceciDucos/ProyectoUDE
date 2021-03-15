package com.proyecto.bombsaway.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.bombsaway.entidades.EntidadBase;

public interface IDAOBase extends JpaRepository<EntidadBase, Integer> {

	@Query(value = "SELECT * FROM bases WHERE id_jugador = ?", nativeQuery = true)
	public EntidadBase findByIdJugador(int idJugador);

}

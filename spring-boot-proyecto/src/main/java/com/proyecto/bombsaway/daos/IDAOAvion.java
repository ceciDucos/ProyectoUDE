package com.proyecto.bombsaway.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.bombsaway.entidades.EntidadAvion;

public interface IDAOAvion extends JpaRepository<EntidadAvion, Integer> {

	@Query(value = "SELECT * FROM aviones WHERE id_jugador = ?", nativeQuery = true)
	public List<EntidadAvion> findAllByIdJugador(int idJugador);

}

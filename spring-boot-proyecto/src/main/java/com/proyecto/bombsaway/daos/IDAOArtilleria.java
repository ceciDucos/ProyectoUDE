package com.proyecto.bombsaway.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.bombsaway.entidades.EntidadArtilleria;

public interface IDAOArtilleria extends JpaRepository<EntidadArtilleria, Integer> {

	@Query(value = "SELECT * FROM artillerias WHERE id_jugador = ?", nativeQuery = true)
	public List<EntidadArtilleria> findAllByIdJugador(int idJugador);

}

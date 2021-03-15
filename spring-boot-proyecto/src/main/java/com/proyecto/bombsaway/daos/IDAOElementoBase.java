package com.proyecto.bombsaway.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.bombsaway.entidades.EntidadElementoBase;

public interface IDAOElementoBase extends JpaRepository<EntidadElementoBase, Integer> {

	@Query(value = "SELECT * FROM elementos_bases WHERE id_base = ?", nativeQuery = true)
	public List<EntidadElementoBase> findAllByIdBase(int idBase);

}

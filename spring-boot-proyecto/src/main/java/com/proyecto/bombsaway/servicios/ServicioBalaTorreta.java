package com.proyecto.bombsaway.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.daos.IDAOBalaTorreta;
import com.proyecto.bombsaway.entidades.EntidadBala;
import com.proyecto.bombsaway.entidades.EntidadBalaTorreta;
import com.proyecto.bombsaway.entidades.EntidadElementoBase;

@Service
public class ServicioBalaTorreta {

	@Autowired
	private IDAOBalaTorreta daoBalaTorreta;

	public EntidadBalaTorreta guardar(EntidadBala bala, EntidadElementoBase torreta) {
		return this.daoBalaTorreta.save(new EntidadBalaTorreta(0, bala, torreta));
	}

}

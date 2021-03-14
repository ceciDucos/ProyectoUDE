package com.proyecto.bombsaway.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.daos.IDAOBalaAvion;
import com.proyecto.bombsaway.entidades.EntidadAvion;
import com.proyecto.bombsaway.entidades.EntidadBala;
import com.proyecto.bombsaway.entidades.EntidadBalaAvion;

@Service
public class ServicioBalaAvion {

	@Autowired
	private IDAOBalaAvion daoBalaAvion;

	public EntidadBalaAvion guardar(EntidadBala bala, EntidadAvion avion) {
		return this.daoBalaAvion.save(new EntidadBalaAvion(0, bala, avion));
	}

}

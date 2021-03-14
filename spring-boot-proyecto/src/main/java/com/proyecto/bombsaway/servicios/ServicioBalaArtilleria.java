package com.proyecto.bombsaway.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.daos.IDAOBalaArtilleria;
import com.proyecto.bombsaway.entidades.EntidadArtilleria;
import com.proyecto.bombsaway.entidades.EntidadBala;
import com.proyecto.bombsaway.entidades.EntidadBalaArtilleria;

@Service
public class ServicioBalaArtilleria {

	@Autowired
	private IDAOBalaArtilleria daoBalaArtilleria;

	public EntidadBalaArtilleria guardar(EntidadBala bala, EntidadArtilleria artilleria) {
		return this.daoBalaArtilleria.save(new EntidadBalaArtilleria(0, bala, artilleria));
	}

}

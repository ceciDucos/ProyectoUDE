package com.proyecto.bombsaway.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.clases.Bala;
import com.proyecto.bombsaway.clases.Posicion;
import com.proyecto.bombsaway.daos.IDAOBala;
import com.proyecto.bombsaway.entidades.EntidadBala;

@Service
public class ServicioBala {

	@Autowired
	private IDAOBala daoBala;

	public EntidadBala guardar(Bala bala) {
		Posicion posicion = bala.getPosicion();
		return this.daoBala.save(
				new EntidadBala(0, posicion.getEjeX(), posicion.getEjeY(), posicion.getAngulo(), bala.isVisible()));
	}

}

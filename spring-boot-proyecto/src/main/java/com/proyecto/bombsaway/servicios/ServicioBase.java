package com.proyecto.bombsaway.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.clases.Base;
import com.proyecto.bombsaway.clases.Posicion;
import com.proyecto.bombsaway.daos.IDAOBase;
import com.proyecto.bombsaway.entidades.EntidadBase;
import com.proyecto.bombsaway.entidades.EntidadJugador;

@Service
public class ServicioBase {

	@Autowired
	private IDAOBase daoBase;

	public EntidadBase guardar(Base base, EntidadJugador jugador) {
		Posicion posicionBase = base.getPosicion();
		return this.daoBase.save(new EntidadBase(0, jugador, posicionBase.getEjeX(), posicionBase.getEjeY(),
				posicionBase.getAngulo(), base.isVisible()));
	}
}

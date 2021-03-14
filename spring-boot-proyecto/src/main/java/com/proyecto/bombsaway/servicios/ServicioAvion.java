package com.proyecto.bombsaway.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.clases.Avion;
import com.proyecto.bombsaway.clases.Posicion;
import com.proyecto.bombsaway.daos.IDAOAvion;
import com.proyecto.bombsaway.entidades.EntidadAvion;
import com.proyecto.bombsaway.entidades.EntidadJugador;

@Service
public class ServicioAvion {

	@Autowired
	private IDAOAvion daoAvion;

	public EntidadAvion guardar(Avion avion, EntidadJugador jugador) {
		Posicion posicion = avion.getPosicion();
		return this.daoAvion.save(new EntidadAvion(0, jugador, posicion.getEjeX(), posicion.getEjeY(),
				posicion.getAngulo(), avion.getEstado(), avion.getVida(), avion.getCombustible(), avion.isTieneBomba(),
				avion.isVisible()));
	}

}

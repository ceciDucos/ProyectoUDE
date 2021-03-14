package com.proyecto.bombsaway.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.clases.Artilleria;
import com.proyecto.bombsaway.clases.Posicion;
import com.proyecto.bombsaway.daos.IDAOArtilleria;
import com.proyecto.bombsaway.entidades.EntidadArtilleria;
import com.proyecto.bombsaway.entidades.EntidadJugador;

@Service
public class ServicioArtilleria {

	@Autowired
	private IDAOArtilleria daoArtilleria;

	public EntidadArtilleria guardar(Artilleria artilleria, EntidadJugador jugador) {
		Posicion posicion = artilleria.getPosicion();
		return this.daoArtilleria.save(new EntidadArtilleria(0, jugador, posicion.getEjeX(), posicion.getEjeY(),
				posicion.getAngulo(), artilleria.isDestruida(), artilleria.isVisible()));
	}

}

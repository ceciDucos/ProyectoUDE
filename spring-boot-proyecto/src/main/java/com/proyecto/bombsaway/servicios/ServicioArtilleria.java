package com.proyecto.bombsaway.servicios;

import java.util.List;

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

	public List<EntidadArtilleria> buscarTodosPorIdJugador(int idJugador) {
		return this.daoArtilleria.findAllByIdJugador(idJugador);
	}

	public EntidadArtilleria guardar(Artilleria artilleria, EntidadJugador jugador) {
		Posicion posicion = artilleria.getPosicion();
		return this.daoArtilleria
				.save(new EntidadArtilleria(0, artilleria.getIdArtilleria(), jugador, posicion.getEjeX(),
						posicion.getEjeY(), posicion.getAngulo(), artilleria.isDestruida(), artilleria.isVisible()));
	}

	public EntidadArtilleria actualizar(EntidadArtilleria artilleria) {
		return this.daoArtilleria.save(artilleria);
	}

}

package com.proyecto.bombsaway.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.clases.Jugador;
import com.proyecto.bombsaway.daos.IDAOJugador;
import com.proyecto.bombsaway.entidades.EntidadJugador;

@Service
public class ServicioJugador {

	@Autowired
	private IDAOJugador daoJugador;

	public EntidadJugador guardar(Jugador jugador) {
		return this.daoJugador.save(new EntidadJugador(0, jugador.getId(), jugador.getNombre()));
	}

}

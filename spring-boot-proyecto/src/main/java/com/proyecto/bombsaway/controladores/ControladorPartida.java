package com.proyecto.bombsaway.controladores;

import com.proyecto.bombsaway.dtos.*;
import com.proyecto.bombsaway.servicios.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorPartida {
	@Autowired
	private ServicioPartida servicioPartida;

	@MessageMapping("/nueva-partida")
	@SendTo("/topic/user")
	public DTOMensaje nuevaPartidaEnEspera(DTOPartidaEnEspera nuevaPartida) {
		this.servicioPartida.crearPartidaEnEspera(nuevaPartida);
		return new DTOMensaje("partida en espera, usuario:  " + nuevaPartida.getNombreJugador() + ",  partida: "
				+ nuevaPartida.getNombrePartida());
	}

	@MessageMapping("/unirse-a-partida")
	@SendTo("/topic/user")
	public DTOMensaje unirseAPartida(DTOUsuario nuevoJugador) {
		return this.servicioPartida.unirseAPartida(nuevoJugador);
	}

	@MessageMapping("/mover-avion")
	public void moverAvion(DTOAvion avionDto) {
		this.servicioPartida.moverAvion(avionDto);
	}

	@MessageMapping("/disparo-bala")
	public void dispararBala(DTOBala balaDto) {
		this.servicioPartida.dispararBala(balaDto);
	}

	@MessageMapping("/colocar-base")
	public void colocarBase(DTOBase baseDto) {
		this.servicioPartida.colocarBase(baseDto);
	}

	@MessageMapping("/tirar-bomba")
	public void tirarBomba(DTOBomba bombaDto) {
		this.servicioPartida.tirarBomba(bombaDto);
	}
	
	@MessageMapping("/colocar-artilleria")
	public void colocarArtilleria(DTOArtilleria artilleriaDto) {
		this.servicioPartida.colocarArtilleria(artilleriaDto);
	}

	@MessageMapping("/primer-disparo")
	public void primerDisparo(DTOBala balaDto) {
		this.servicioPartida.primerDisparoBala(balaDto);
	}

	@MessageMapping("/mover-artilleria")
	public void moverArtilleria(DTOArtilleria artilleriaDto) {
		this.servicioPartida.moverArtilleria(artilleriaDto);
	}
}

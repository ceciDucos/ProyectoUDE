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

	@MessageMapping("/user")
	@SendTo("/topic/user")
	public UserResponse getUser(User user) {
		return new UserResponse("Hi " + user.getName());
	}

	@MessageMapping("/nueva-partida")
	@SendTo("/topic/user")
	public UserResponse getUser(DTOPartidaEnEspera nuevaPartida) {
		this.servicioPartida.crearPartidaEnEspera(nuevaPartida);
		return new UserResponse("partida en espera, usuario:  " + nuevaPartida.getNombreJugador() + ",  partida: "
				+ nuevaPartida.getNombrePartida());
	}

	@MessageMapping("/unirse-a-partida")
	@SendTo("/topic/user")
	public DTOMensaje getUser(DTOUsuario nuevoJugador) {
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

	@MessageMapping("/disparo-bala2")
	public void dispararBala2(DTOBala balaDto) {
		this.servicioPartida.dispararBala2(balaDto);
	}
}

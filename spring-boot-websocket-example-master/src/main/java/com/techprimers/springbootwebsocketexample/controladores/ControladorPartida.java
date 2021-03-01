package com.techprimers.springbootwebsocketexample.controladores;

import com.techprimers.springbootwebsocketexample.dtos.*;
import com.techprimers.springbootwebsocketexample.servicios.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.json.JsonObject;

@Controller
public class ControladorPartida {
    @Autowired
    private ServicioPartida servicioIniciarPartida;

    @MessageMapping("/user")
    @SendTo("/topic/user")
    public UserResponse getUser(User user) {
        return new UserResponse("Hi " + user.getName());
    }

    @MessageMapping("/nueva-partida")
    @SendTo("/topic/user")
    public UserResponse getUser(DTOPartidaEnEspera nuevaPartida) {
        System.out.println("usuario que llega: " + nuevaPartida.getNombreJugador());
        this.servicioIniciarPartida.crearPartidaEnEspera(nuevaPartida);
        return new UserResponse("partida en espera, usuario:  " +
                nuevaPartida.getNombreJugador() + ",  partida: " + nuevaPartida.getNombrePartida()
        );
    }

    @MessageMapping("/unirse-a-partida")
    @SendTo("/topic/user")
    public DTOMensaje getUser(DTOUsuario nuevoJugador) {
        return this.servicioIniciarPartida.unirseAPartida(nuevoJugador);
    }

    @MessageMapping("/mover-avion")
    public void moverAvion(DTOAvion avionDto) {
        this.servicioIniciarPartida.moverAvion(avionDto);
    }
}


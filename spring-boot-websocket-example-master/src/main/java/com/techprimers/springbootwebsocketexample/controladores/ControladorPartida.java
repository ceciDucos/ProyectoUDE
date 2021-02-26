package com.techprimers.springbootwebsocketexample.controladores;

import com.techprimers.springbootwebsocketexample.dtos.DTOAvion;
import com.techprimers.springbootwebsocketexample.dtos.DTOPartida;
import com.techprimers.springbootwebsocketexample.dtos.User;
import com.techprimers.springbootwebsocketexample.dtos.UserResponse;
import com.techprimers.springbootwebsocketexample.servicios.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

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
    public UserResponse getUser(DTOPartida nuevaPartida) {
        this.servicioIniciarPartida.addPartida(nuevaPartida);
        System.out.println(nuevaPartida.toString());
        return new UserResponse("partida iniciada correctamente, jugadores:  " +
                nuevaPartida.getNombreJugadorUno() + ",  " + nuevaPartida.getNombreJugadorDos()
        );
    }

    @MessageMapping("/mover-avion")
    @SendTo("/topic/user")
    public UserResponse getUser(DTOAvion avionDto) {
        System.out.println(avionDto.toString());
        return new UserResponse("avion movido correctamente");
    }

}

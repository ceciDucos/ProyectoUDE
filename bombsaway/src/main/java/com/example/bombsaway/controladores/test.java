package com.example.bombsaway.controladores;
import org.springframework.web.bind.annotation.*;

@RestController
public class test {

    @RequestMapping("/helloWorld")
    public String getAlgo() {
        return "Hola estamos probando";
    }

    @RequestMapping("/nuevaPartida/{nombreJugador}")
    public String getPartida(@PathVariable String nombreJugador) {
        return "Hola " + nombreJugador + "! Bienvenido a la partida.";
    }
}

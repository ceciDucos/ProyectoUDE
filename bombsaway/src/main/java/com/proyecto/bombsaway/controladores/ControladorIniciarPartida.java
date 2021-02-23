package com.proyecto.bombsaway.controladores;

import com.proyecto.bombsaway.DTOS.DTOPartida;
import com.proyecto.bombsaway.DTOS.DTOPartidaCompleto;
import com.proyecto.bombsaway.servicios.ServicioIniciarPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/partida")
@RestController
public class ControladorIniciarPartida {
    @Autowired
    private ServicioIniciarPartida servicioIniciarPartida;

    @Autowired
    public ControladorIniciarPartida(ServicioIniciarPartida servicio) {
        this.servicioIniciarPartida = servicio;
    }

    @PostMapping
    public void addPartida(@RequestBody DTOPartida partida) {
        this.servicioIniciarPartida.addPartida(partida);
    }

    @GetMapping
    public List<DTOPartidaCompleto> getPartidas() {
        List<DTOPartidaCompleto> res = this.servicioIniciarPartida.getListPartidas();
        for (DTOPartidaCompleto dto: res) {
            System.out.println(dto.toString());
        }
        return res;
    }
}

//    @PostMapping
//    public void addAvion(@RequestBody Avion avion){
//        this.servicioIniciarPartida.addAvion(avion);
//    }
//


    //    @PostMapping
//    public void addAvion(@RequestBody Avion avion){
//        this.servicioIniciarPartida.addAvion(avion);
//    }
//
//    @RequestMapping("/nuevaPartida/{nombreJugadorUno}/{nombreJugadorDos}/{nombrePartida}")
//    public String iniciarPartida(@PathVariable String nombreJugadorUno, @PathVariable String nombreJugadorDos,
//                                 @PathVariable String nombrePartida) {
//        ServicioIniciarPartida servicio = new ServicioIniciarPartida();
//        servicio.iniciarPartida(nombreJugadorUno, nombreJugadorDos, nombrePartida);
//        return "partida iniciada correctamente con los siguientes datos: "
//                + "jugadorUno: " + nombreJugadorUno
//                + "jugadorDos: " + nombreJugadorDos
//                + "partida: " + nombrePartida;
//    }



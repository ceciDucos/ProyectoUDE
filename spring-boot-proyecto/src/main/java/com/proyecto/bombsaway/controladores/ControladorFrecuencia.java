package com.proyecto.bombsaway.controladores;

import com.proyecto.bombsaway.servicios.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class ControladorFrecuencia {

    @Autowired
    ServicioPartida servicioPartida;

    @Scheduled(fixedDelay = 600)
    public void sendActualizarCombistible() {
        this.servicioPartida.updateCombustibleAviones();
    }

    @Scheduled(fixedDelay = 100)
    public void sendActualizarVisibilidad() {
        this.servicioPartida.updateVisibilidad3(1);
        this.servicioPartida.updateVisibilidad3(2);
    }

}

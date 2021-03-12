package com.proyecto.bombsaway.controladores;

import com.proyecto.bombsaway.servicios.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class ControladorFrecuencia {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    ServicioPartida servicioPartida;

    @Scheduled(fixedDelay = 60)
    public void sendActualizarCombistible() {
        this.servicioPartida.updateCombustibleAviones();
    }

    @Scheduled(fixedDelay = 60)
    public void sendActualizarVisibilidad() {
        String datajugadorUno = this.servicioPartida.updateVisibilidad3(1);
        template.convertAndSend("/topic/elementos-visibles", datajugadorUno);
        String datajugadorDos = this.servicioPartida.updateVisibilidad3(2);
        template.convertAndSend("/topic/elementos-visibles", datajugadorDos);
    }

}

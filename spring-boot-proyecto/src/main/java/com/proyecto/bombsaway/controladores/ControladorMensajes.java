package com.proyecto.bombsaway.controladores;


import javax.json.Json;
import javax.json.JsonObject;

import com.proyecto.bombsaway.clases.Partida;
import com.proyecto.bombsaway.manejadores.ManejadorPartida;
import com.proyecto.bombsaway.servicios.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@EnableScheduling
@Configuration
public class ControladorMensajes {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    ServicioPartida servicioPartida;

    @Scheduled(fixedDelay = 500)
    public void sendActualizarCombistible() {
        this.servicioPartida.updateCombustibleAviones();
    }

    public void sendAvionesEnemigos(String data) {
        template.convertAndSend("/topic/aviones-enemigos", data);
    }

    public void sendEstallarAviones(String data) {
        template.convertAndSend("/topic/estallar-aviones", data);
    }

    public void sendBajarVidaAvion(String data) {
        template.convertAndSend("/topic/bajar-vida-avion", data);
    }

    public void sendPosicionBala(String data) {
        template.convertAndSend("/topic/posicion-bala", data);
    }
    
    public void sendColocarBases(String data) {
    	template.convertAndSend("/topic/actualizar-bases", data);
    }
    
    public void sendActualizarArtilleria(String data) {
    	template.convertAndSend("/topic/actualizar-artilleria", data);
    }
    
    public void sendDestruirArtilleria(String data) {
    	template.convertAndSend("/topic/destruir-artilleria", data);
    }
    
    public void sendEstadoElementosBase(String data) {
    	template.convertAndSend("/topic/estado-elementos-base", data);
    }

    public void sendArtilleriaMovida(String data) {
        template.convertAndSend("/topic/artilleria-movida", data);
    }

    public void sendElementosVisibles(String data) {
        template.convertAndSend("/topic/elementos-visibles", data);
    }

    public void sendAvionEnemigoVisible(String data) {
        template.convertAndSend("/topic/avion-enemigo-visible", data);
    }

    public void sendErrores(String data) {
        template.convertAndSend("/topic/errores", data);
    }
}


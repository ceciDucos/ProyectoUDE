package com.proyecto.bombsaway.config;


import javax.json.Json;
import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class SchedulerConfig {

    @Autowired
    SimpMessagingTemplate template;

//    @Scheduled(fixedDelay = 3000)
//    public void sendAdhocMessages() {
//        template.convertAndSend("/topic/user", value);
//    }

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

    public void sendErrores(String data) {
        template.convertAndSend("/topic/errores", data);
    }
}

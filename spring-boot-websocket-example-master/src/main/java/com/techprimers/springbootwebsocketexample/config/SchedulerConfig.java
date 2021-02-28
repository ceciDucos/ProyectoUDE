package com.techprimers.springbootwebsocketexample.config;


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
//        JsonObject value = Json.createObjectBuilder()
//                .add("nombrePartida", "nuevaPartida")
//                .add("jugadorUno", Json.createObjectBuilder()
//                        .add("nombreJugadorUno", "Smith")
//                        .add("avionUno",Json.createObjectBuilder()
//                                .add("ejeX", "100")
//                                .add("ejeY", "100")
//                                .add("angulo", "100"))
//                        .add("avionDos",Json.createObjectBuilder()
//                                .add("ejeX", "100")
//                                .add("ejeY", "100")
//                                .add("angulo", "100"))
//                        .add("avionTres",Json.createObjectBuilder()
//                                .add("ejeX", "100")
//                                .add("ejeY", "100")
//                                .add("angulo", "100"))
//                        .add("avionCuatro",Json.createObjectBuilder()
//                                .add("ejeX", "100")
//                                .add("ejeY", "100")
//                                .add("angulo", "100")))
//                    .add("jugadorUno", Json.createObjectBuilder()
//                            .add("nombreJugadorDos", "Lincon")
//                            .add("avionUno",Json.createObjectBuilder()
//                                    .add("ejeX", "100")
//                                    .add("ejeY", "100")
//                                    .add("angulo", "100"))
//                            .add("avionDos",Json.createObjectBuilder()
//                                    .add("ejeX", "100")
//                                    .add("ejeY", "100")
//                                    .add("angulo", "100"))
//                            .add("avionTres",Json.createObjectBuilder()
//                                    .add("ejeX", "100")
//                                    .add("ejeY", "100")
//                                    .add("angulo", "100"))
//                            .add("avionCuatro",Json.createObjectBuilder()
//                                    .add("ejeX", "100")
//                                    .add("ejeY", "100")
//                                    .add("angulo", "100"))
//                ).build();
//        template.convertAndSend("/topic/user", value);
//    }
}


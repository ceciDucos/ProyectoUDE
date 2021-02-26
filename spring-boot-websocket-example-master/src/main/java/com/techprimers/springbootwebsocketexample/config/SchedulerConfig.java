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

    @Scheduled(fixedDelay = 3000)
    public void sendAdhocMessages() {
        JsonObject value = Json.createObjectBuilder()
                .add("nombrePartida", "nuevaPartida")
                .add("jugadorUno", Json.createObjectBuilder()
                        .add("nombreJugadorUno", "Smith")
                        .add("avionUno",Json.createObjectBuilder()
                                .add("ejeX", "100")
                                .add("ejeY", "100")
                                .add("angulo", "100"))
                        .add("avionDos",Json.createObjectBuilder()
                                .add("ejeX", "100")
                                .add("ejeY", "100")
                                .add("angulo", "100"))
                        .add("avionTres",Json.createObjectBuilder()
                                .add("ejeX", "100")
                                .add("ejeY", "100")
                                .add("angulo", "100"))
                        .add("avionCuatro",Json.createObjectBuilder()
                                .add("ejeX", "100")
                                .add("ejeY", "100")
                                .add("angulo", "100")))
                    .add("jugadorUno", Json.createObjectBuilder()
                            .add("nombreJugadorDos", "Lincon")
                            .add("avionUno",Json.createObjectBuilder()
                                    .add("ejeX", "100")
                                    .add("ejeY", "100")
                                    .add("angulo", "100"))
                            .add("avionDos",Json.createObjectBuilder()
                                    .add("ejeX", "100")
                                    .add("ejeY", "100")
                                    .add("angulo", "100"))
                            .add("avionTres",Json.createObjectBuilder()
                                    .add("ejeX", "100")
                                    .add("ejeY", "100")
                                    .add("angulo", "100"))
                            .add("avionCuatro",Json.createObjectBuilder()
                                    .add("ejeX", "100")
                                    .add("ejeY", "100")
                                    .add("angulo", "100"))
                ).build();
        template.convertAndSend("/topic/user", value);
    }
}
















//        DTOAvion dtoAvion = new DTOAvion("100", "100", "100");
//        DTOPartida partida = new DTOPartida("partida nueva", "rojo", "azul", dtoAvion.toString());
//        partida.setAvion(dtoAvion);

//                                "21 2nd Street")
//                        .add("city", "New York")
//                        .add("state", "NY")
//                        .add("postalCode", "10021"))
//                .add("phoneNumber", Json.createArrayBuilder()
//                        .add(Json.createObjectBuilder()
//                                .add("type", "home")
//                                .add("number", "212 555-1234"))
//                        .add(Json.createObjectBuilder()
//                                .add("type", "fax")
//                                .add("number", "646 555-4567")))
//                .build();

//        ObjectMapper mapper = new ObjectMapper();
//            Gson gson = new Gson();
//            UserResponse user = new UserResponse("Frecuencia de actualización");
//            String jsonString = gson.toJson(user);
//            System.out.println(jsonString);

//            String json = mapper.writeValueAsString(user);
//            UserResponse toSend = new UserResponse(json);
//            System.out.println("ResultingJSONstring = " + json);

package com.proyecto.bombsaway;

import com.proyecto.bombsaway.config.SchedulerConfig;
import com.proyecto.bombsaway.daos.DAOPartida;
import com.proyecto.bombsaway.manejadores.ManejadorPartida;
import com.proyecto.bombsaway.servicios.ServicioPartida;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebsocketExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebsocketExampleApplication.class, args);
//		ServicioPartida servicioPartida = new ServicioPartida(new ManejadorPartida(), new DAOPartida(),
//				new SchedulerConfig());
//		servicioPartida.runTests();
	}
}

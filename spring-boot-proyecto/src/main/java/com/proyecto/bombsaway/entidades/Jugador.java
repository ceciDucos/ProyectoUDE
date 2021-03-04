//package com.techprimers.springbootwebsocketexample.entidades;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "jugadores")
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class Jugador {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id_jugador")
//	private int id;
//
//	@Column(name = "nombre", nullable = false, unique = true, length = 50)
//	private String nombre;
//
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id_avion")
//	private List<Avion> aviones;
//
//}

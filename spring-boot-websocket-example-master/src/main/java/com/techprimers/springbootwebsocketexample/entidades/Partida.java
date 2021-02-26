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
//@Table(name = "partidas")
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class Partida {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id_partida")
//	private int id;
//
//	@Column(name = "nombre", nullable = false, unique = true, length = 50)
//	private String nombre;
//
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "partidas_jugadores", joinColumns = @JoinColumn(name = "id_partida"), inverseJoinColumns = @JoinColumn(name = "id_jugador"))
//	private List<Jugador> jugadores;
//
//}

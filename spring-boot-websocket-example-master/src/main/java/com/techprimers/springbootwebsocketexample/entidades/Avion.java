//package com.techprimers.springbootwebsocketexample.entidades;
//
//import com.techprimers.springbootwebsocketexample.clases.EstadoAvion;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "aviones")
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class Avion {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id_avion")
//	private int id;
//
//	@OneToOne
//	@JoinColumn(name = "id_posicion_avion", nullable = false)
//	private Posicion posicion;
//
//	@Column(name = "estado")
//	@Enumerated(EnumType.STRING)
//	private EstadoAvion estado;
//
//	@Column(name = "combustible")
//	private int combustible;
//
//	@Column(name = "vida", nullable = false)
//	private int vida;
//
//	@Column(name = "tiene_bomba", nullable = false)
//	private boolean tieneBomba;
//
//	@ManyToOne
//	@JoinColumn(name = "id_jugador")
//	private Jugador jugador;
//
//}

//package com.techprimers.springbootwebsocketexample.entidades;
//
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
//public class Artilleria {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id_artilleria")
//	private int id;
//
//	@OneToOne
//	@JoinColumn(name = "id_posicion_artilleria", nullable = false)
//	private Posicion posicion;
//
//}

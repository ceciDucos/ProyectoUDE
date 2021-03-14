package com.proyecto.bombsaway.entidades;

import javax.persistence.*;

@Entity
@Table(name = "balas_aviones")
public class EntidadBalaAvion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bala_avion")
	private int id;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_bala")
	private EntidadBala bala;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_avion")
	private EntidadAvion avion;

	public EntidadBalaAvion() {
	}

	public EntidadBalaAvion(int id, EntidadBala bala, EntidadAvion avion) {
		this.id = id;
		this.bala = bala;
		this.avion = avion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EntidadBala getBala() {
		return bala;
	}

	public void setBala(EntidadBala bala) {
		this.bala = bala;
	}

	public EntidadAvion getAvion() {
		return avion;
	}

	public void setAvion(EntidadAvion avion) {
		this.avion = avion;
	}

}

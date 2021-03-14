package com.proyecto.bombsaway.entidades;

import javax.persistence.*;

@Entity
@Table(name = "balas_artillerias")
public class EntidadBalaArtilleria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bala_artilleria")
	private int id;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_bala")
	private EntidadBala bala;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_artilleria")
	private EntidadArtilleria artilleria;

	public EntidadBalaArtilleria() {
	}

	public EntidadBalaArtilleria(int id, EntidadBala bala, EntidadArtilleria artilleria) {
		this.id = id;
		this.bala = bala;
		this.artilleria = artilleria;
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

	public EntidadArtilleria getArtilleria() {
		return artilleria;
	}

	public void setArtilleria(EntidadArtilleria artilleria) {
		this.artilleria = artilleria;
	}

}

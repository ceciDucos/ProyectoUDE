package com.proyecto.bombsaway.entidades;

import javax.persistence.*;

@Entity
@Table(name = "balas_torretas")
public class EntidadBalaTorreta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bala_torreta")
	private int id;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_bala")
	private EntidadBala bala;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_elemento_base")
	private EntidadElementoBase torreta;

	public EntidadBalaTorreta() {
	}

	public EntidadBalaTorreta(int id, EntidadBala bala, EntidadElementoBase torreta) {
		this.id = id;
		this.bala = bala;
		this.torreta = torreta;
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

	public EntidadElementoBase getTorreta() {
		return torreta;
	}

	public void setTorreta(EntidadElementoBase torreta) {
		this.torreta = torreta;
	}

}
